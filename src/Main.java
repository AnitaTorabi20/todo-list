import db.exception.*;
import todo.entity.*;
import todo.service.*;
import db.*;
import java.util.Scanner;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("To-Do List Manager");
        printHelp();

        while (true) {
            System.out.print("\nEnter command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            try {
                switch (command) {
                    case "add task":
                        addTask();
                        break;
                    case "add step":
                        addStep();
                        break;
                    case "delete":
                        deleteEntity();
                        break;
                    case "update task":
                        updateTask();
                        break;
                    case "update step":
                        updateStep();
                        break;
                    case "get task-by-id":
                        getTaskById();
                        break;
                    case "get all-tasks":
                        getAllTasks();
                        break;
                    case "get incomplete-tasks":
                        getIncompleteTasks();
                        break;
                    case "help":
                        printHelp();
                        break;
                    case "exit":
                        System.out.println("Exiting program...");
                        return;
                    default:
                        System.out.println("Invalid command! Type 'help' for available commands.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printHelp() {
        System.out.println("\nAvailable commands:");
        System.out.println("add task       - Add a new task");
        System.out.println("add step       - Add a new step to a task");
        System.out.println("delete         - Delete a task or step");
        System.out.println("update task    - Update a task");
        System.out.println("update step    - Update a step");
        System.out.println("get task-by-id - View a specific task");
        System.out.println("get all-tasks  - List all tasks");
        System.out.println("get incomplete-tasks - List incomplete tasks");
        System.out.println("help           - Show this help message");
        System.out.println("exit           - Exit the program");
    }

    private static void addTask() {
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Due date (yyyy-mm-dd): ");
            Date dueDate = dateFormat.parse(scanner.nextLine());

            Task task = TaskService.addTask(title, description, dueDate);
            System.out.println("Task saved successfully.");
            System.out.println("ID: " + task.getId());
        } catch (Exception e) {
            System.out.println("Cannot save task. Error: " + e.getMessage());
        }
    }

    private static void addStep() {
        try {
            System.out.print("Task ID: ");
            int taskId = Integer.parseInt(scanner.nextLine());

            System.out.print("Title: ");
            String title = scanner.nextLine();

            Step step = StepService.saveStep(taskId, title);
            if (step != null) {
                System.out.println("Step saved successfully.");
                System.out.println("ID: " + step.getId());
                System.out.println("Creation Date: " + step.getCreationDate());
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Task ID must be a number!");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void deleteEntity() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (TaskService.deleteEntity(id)) {
                System.out.println("Task with ID=" + id + " successfully deleted.");
                return;
            }

            if (StepService.deleteStep(id)) {
                System.out.println("Step with ID=" + id + " successfully deleted.");
                return;
            }

            System.out.println("Cannot delete entity with ID=" + id);
            System.out.println("Error: Something happend");
        } catch (Exception e) {
            System.out.println("Error deleting entity: " + e.getMessage());
        }
    }

    private static void updateTask() {
        try {
            System.out.print("Task ID: ");
            int taskId = Integer.parseInt(scanner.nextLine());

            Task task = TaskService.getTaskById(taskId);
            if (task == null) {
                System.out.println("Task with ID=" + taskId + " not found.");
                return;
            }

            System.out.print("Field (title/description/status): ");
            String field = scanner.nextLine().toLowerCase();

            if (!(field.equals("title") || field.equals("description") || field.equals("status"))) {
                System.out.println("Invalid field. Please use 'title', 'description', or 'status'.");
                return;
            }

            System.out.print("New Value: ");
            String newValue = scanner.nextLine();

            String oldValue = "";
            if (field.equals("title")) {
                oldValue = task.getTitle();
            } else if (field.equals("description")) {
                oldValue = task.getDescription();
            } else if (field.equals("status")) {
                oldValue = task.getStatus().toString();
            }

            boolean success = TaskService.updateTask(taskId, field, newValue);
            if (success) {
                System.out.println("Successfully updated the task.");
                System.out.println("Field: " + field);
                System.out.println("Old Value: " + oldValue);
                System.out.println("New Value: " + newValue);
                System.out.println("Modification Date: " + task.getCreationDate());
            } else {
                System.out.println("Cannot update task with ID=."+ taskId);
            }
        } catch (Exception e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }

    private static void updateStep() {
        try {
            System.out.print("Step ID: ");
            int stepId = Integer.parseInt(scanner.nextLine());

            System.out.print("Field (title/status): ");
            String field = scanner.nextLine().toLowerCase();

            System.out.print("New Value: ");
            String newValue = scanner.nextLine();

            Step step = StepService.getStepById(stepId);
            if (step == null) {
                System.out.printf("Cannot update step with ID=%d.\nError: Cannot find entity with ID=%d\n", stepId, stepId);
                return;
            }

            String oldValue = field.equals("status") ? step.getStatus().toString() : step.getTitle();

            if (field.equals("title")) {
                step.setTitle(newValue);
                Database.update(step);
            }
            else if (field.equals("status") && newValue.equalsIgnoreCase("completed")) {
                step.setStatus(Step.Status.Completed);
                Database.update(step);

                checkTaskStatus(step.getTaskRef());

            } else {
                System.out.println("Invalid input!");
                return;
            }

            System.out.printf("""
            Successfully updated the step.
            Field: %s
            Old Value: %s
            New Value: %s
            Modification Date: %s
            """, field, oldValue, newValue, new Date());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void checkTaskStatus(int taskId) {
        try {
            Task task = TaskService.getTaskById(taskId);
            if (task == null) return;

            List<Step> steps = StepService.getStepsByTaskRef(taskId);

            boolean allCompleted = steps.stream()
                    .allMatch(s -> s.getStatus() == Step.Status.Completed);

            if (allCompleted) {
                task.setStatus(Task.Status.Completed);
            }
            else if (task.getStatus() == Task.Status.NotStarted) {
                task.setStatus(Task.Status.InProgress);
            }

            Database.update(task);
        } catch (InvalidEntityException e) {
            System.out.println("Error updating task status: " + e.getMessage());
        }
    }

    private static void getTaskById() {
        try {
            System.out.print("Task ID: ");
            int taskId = Integer.parseInt(scanner.nextLine());

            Task task = TaskService.getTaskById(taskId);
            if (task == null) {
                System.out.println("Task not found with ID=" + taskId);
                return;
            }

            System.out.println("\nTask Details:");
            System.out.println("ID: " + task.getId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Due Date: " + dateFormat.format(task.getDueDate()));
            System.out.println("Status: " + task.getStatus());

            List<Step> steps = StepService.getStepsByTaskRef(taskId);
            if (!steps.isEmpty()) {
                System.out.println("\nSteps:");
                for (Step step : steps) {
                    System.out.println("+ " + step.getTitle() +"\n" +
                            " ID: " + step.getId() +
                            "\n Status: " + step.getStatus());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getAllTasks() {
        try {
            List<Task> tasks = TaskService.getAllTasks();
            if (tasks.isEmpty()) {
                System.out.println("No tasks found.");
                return;
            }

            System.out.println("\nAll Tasks:");
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId());
                System.out.println("Title: " + task.getTitle());
                System.out.println("Due Date: " + dateFormat.format(task.getDueDate()));
                System.out.println("Status: " + task.getStatus());

                List<Step> steps = StepService.getStepsByTaskRef(task.getId());
                if (!steps.isEmpty()) {
                    System.out.println("Steps:");
                    for (Step step : steps) {
                        System.out.println("    + " + step.getTitle() + ":");
                        System.out.println("        ID: " + step.getId());
                        System.out.println("        Status: " + step.getStatus());
                    }
                }

                System.out.println("----------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getIncompleteTasks() {
        try {
            List<Task> tasks = TaskService.getAllTasks();
            if (tasks.isEmpty()) {
                System.out.println("No tasks found.");
                return;
            }

            System.out.println("\nIncomplete Tasks:");
            for (Task task : tasks) {
                if (task.getStatus() != Task.Status.Completed) {
                    System.out.println("ID: " + task.getId());
                    System.out.println("Title: " + task.getTitle());
                    System.out.println("Due Date: " + dateFormat.format(task.getDueDate()));
                    System.out.println("Status: " + task.getStatus());

                    List<Step> steps = StepService.getStepsByTaskRef(task.getId());
                    if (!steps.isEmpty()) {
                        System.out.println("Steps:");
                        for (Step step : steps) {
                            System.out.println("    + " + step.getTitle() + ":");
                            System.out.println("        ID: " + step.getId());
                            System.out.println("        Status: " + step.getStatus());
                        }
                    }
                    System.out.println("----------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}