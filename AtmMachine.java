
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

class Customer {
    String accNo;
    String pin;
    double balance;

    public Customer(String acc, String pinNo, double bal) {
        this.accNo = acc;
        this.pin = pinNo;
        this.balance = bal;
    }

    public String getAccNo() {
        return accNo;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }
}

class Bank {
    private String name;
    private String address;
    private String contactNumber;

    public Bank(String name, String address, String contactNumber) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}

class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class AtmMachine {
    public static void main(String[] args) {
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> m1 = new HashMap<>();
        List<Admin> admins = new ArrayList<>();
        List<Bank> banks = new ArrayList<>();

        Bank bank1 = new Bank("KVB Bank", "123 Main Street, Tamilnadu, India", "+1234567890");
        banks.add(bank1);

        Admin admin1 = new Admin("admin", "admin123");
        admins.add(admin1);
        
        Customer customer1 = new Customer("783027870408", "2003", 13000);
        List<String> customer1Data = new ArrayList<>();
        customer1Data.add(customer1.getPin());
        customer1Data.add(String.valueOf(customer1.getBalance()));
        m1.put(customer1.getAccNo(), customer1Data);

        do {
            System.out.println("WELCOME TO " + bank1.getName() + " ATM");
            System.out.println("Choose user type:");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Bank");
            System.out.println("4. Exit");
            System.out.println();
            int userTypeChoice = sc.nextInt();

            switch (userTypeChoice) {
                case 1:
                    customerLogin(m1, sc);
                    break;
                case 2:
                    adminLogin(admins, sc);
                    break;
                case 3:
                    System.out.println("Logged in as Bank");
                    System.out.println();
                    // bank functions yet to be added 
                    break;
                case 4:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    System.out.println();
                    break;
            }
        } while (flag);

        sc.close();
    }
    
    private static void customerOperations(Map<String, List<String>> m1, String accNo, Scanner scanner) {
        boolean flag = true;
        do {
            System.out.println("Choose operation:");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Change Pin");
            System.out.println("5. Generate pin");
            
            System.out.println("6. Back to main menu");
            System.out.println();
            int operationChoice = scanner.nextInt();

            switch (operationChoice) {
                case 1:
                    System.out.println("Enter the amount to be withdrawn:");
                    int amountWithdrawal = scanner.nextInt();
                    withdrawalF(m1, accNo, amountWithdrawal);
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Enter the amount to be deposited:");
                    double amountDeposit = scanner.nextDouble();
                    System.out.println();
                    depositF(m1, accNo, amountDeposit);
                    break;
                case 3:
                    checkBalanceF(m1, accNo);
                    System.out.println();
                    break;
                case 4:
                    changePinF(m1, accNo, scanner);
                case 5:
                    System.out.println("Pin Generation");
                    System.out.println("Your generated Pin is"+generateRandomPinF(4));
                    break;
                   
                case 6:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    System.out.println();
                    break;
            }
        } while (flag);
    }

    private static void customerLogin(Map<String, List<String>> m1, Scanner scanner) {
        System.out.println("Enter account number:");
        String accNo = scanner.next();
        List<String> customerData = m1.get(accNo);
        if (customerData != null) {
            System.out.println("Enter PIN:");
            String enteredPin = scanner.next();
            String actualPin = customerData.get(0);
            if (enteredPin.equals(actualPin)) {
                customerOperations(m1, accNo, scanner);
            } else {
                System.out.println("Invalid PIN.");
            }
        } else {
            System.out.println("Account not found with this username");
        }
    }
    

    private static void adminLogin(List<Admin> admins, Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();
        boolean isAdminValid = false;
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                isAdminValid = true;
                break;
            }
        }
        if (isAdminValid) {
            System.out.println("Logged in as Admin");
            // Additional admin functionalities can be added here
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void depositF(Map<String, List<String>> m1, String accNo, double amount) {
        List<String> customerData = m1.get(accNo);
        if (customerData != null) {
            double balance = Double.parseDouble(customerData.get(1));
            balance += amount;
            customerData.set(1, String.valueOf(balance));
            System.out.println("Deposit successful. Your new balance is: " + balance);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawalF(Map<String, List<String>> m1, String accNo, int amount) {
        List<String> customerData = m1.get(accNo);
        if (customerData != null) {
            double balance = Double.parseDouble(customerData.get(1));
            if (balance >= amount) {
                balance -= amount;
                customerData.set(1, String.valueOf(balance));
                System.out.println("Withdrawal successful. Your new balance is: " + balance);
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void checkBalanceF(Map<String, List<String>> m1, String accNo) {
        List<String> customerData = m1.get(accNo);
        if (customerData != null) {
            double balance = Double.parseDouble(customerData.get(1));
            System.out.println("Your current balance is " + balance);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void changePinF(Map<String, List<String>> customerDataMap, String accNo, Scanner scanner) {
        System.out.println("Enter current PIN:");
        String currentPin = scanner.next();
        List<String> customerData = customerDataMap.get(accNo);
        if (customerData != null) {
            if (currentPin.equals(customerData.get(0))) {
                System.out.println("Enter new PIN:");
                String newPin = scanner.next();
                customerData.set(0, newPin);
                System.out.println("PIN changed successfully.");
            } else {
                System.out.println("Invalid PIN.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }
   

    private static String generateRandomPinF(int length) {
        Random random = new Random();
        StringBuilder pinBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            pinBuilder.append(random.nextInt(10));
        }
        return pinBuilder.toString();
    }

    // transfer money , mini statement 
}
