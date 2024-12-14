public class Manager extends User {
    private Connectable db;
    private Table[] tables;
    private SalespersonTable st;
    private ManufacturerTable mt;
    private PartTable pt;

    public Manager(Table[] tables) {
        this.tables = tables;
        for (Table t: tables) {
            if (t instanceof SalespersonTable) { st = (SalespersonTable) t; }
            if (t instanceof ManufacturerTable) { mt = (ManufacturerTable) t; }
            if (t instanceof PartTable) { pt = (PartTable) t; }
        }
    }

    @Override
    public void executeMenu() {
        while (true) {
            System.out.println("\n-----Operations for manager menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. List all salespersons");
            System.out.println("2. Count the no. of sales record of each salesperson under a specific range on years of experience");
            System.out.println("3. Show the total sales value of each manufacturer");
            System.out.println("4. Show the N most popular part");
            System.out.println("5. Return to the main menu");
            System.out.print("Enter Your Choice: ");

            int choice = Console.readInt("Enter Your Choice: ", 1, 5);

            switch (choice) {
                case 1:
                    listSalespersons();
                    break;
                case 2:
                    countTransactions();
                    break;
                case 3:
                    showManufacturerSales();
                    break;
                case 4:
                    showPopularParts();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void listSalespersons() {
        System.out.println("Choose ordering:");
        System.out.println("1. By ascending order");
        System.out.println("2. By descending order");
        int ordering = Console.readInt("Choose the list ordering: ", 1, 2);

        // pass ordering to queryTable overloaded with ordering param
        st.querySalespersonByExp(ordering);
    }

    private void countTransactions() {
        int lowerBound = Console.readInt("Type in the lower bound for years of experience: ");
        int upperBound = Console.readInt("Type in the upper bound for years of experience: ");

        st.querySalespersonTransaction(lowerBound, upperBound);
    }

    private void showManufacturerSales() {
        mt.queryManufacturerSales();
    }

    private void showPopularParts() {
        int n = Console.readInt("Type in the number of parts: ");

        pt.queryPopularParts(n);
    }
}
