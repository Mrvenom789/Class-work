//Zachary Hull
//Dr. Steinberg
//COP3223 section 2
//Large program 2

#include <stdio.h>

void Greeting(); //welcome the user to their dining account
void DisplayMenu(); //display food menu
void Purchase(double *balance); //user will make a purchase
void ViewAccount(double *balance); //display current account balance of user
void Transaction(double *balance, double price); //transaction with user account
void Reload(double *balance); //reload your account balance

int main()
{
    char option; //define variables used throughout the code
    double balance = 0;
    double price;

    Greeting(); //function call\

    do{

    printf("***********************************************************************************\n");
    printf("Please select one of the following options.\n"); //options for user to choose
    printf("P: Make a Purchase at Knightstop\n");
    printf("V: View Knight's Account Summary\n");
    printf("B: Browse Inventory at Knightstop\n");
    printf("R: Reload Knight's Account\n");
    printf("E: Exit\n");
    printf("***********************************************************************************\n");
    printf("Please select what you would like to do.\n");
    printf("Option selected: "); //user selects option from the greeting menu
    scanf(" %c", &option);
    printf("\n");

    switch(option)
    {
    //function calls for purchase
    case 80:
        Purchase(&balance);
        break;
    case 112:
        Purchase(&balance);
        break;

    //function calls for viewing account
    case 86:
        ViewAccount(&balance);
        break;
    case 118:
        ViewAccount(&balance);
        break;

    //function calls for browsing menu
    case 66:
        DisplayMenu();
        break;
    case 98:
        DisplayMenu();
        break;

    //function call for reloading balance
    case 82:
        printf("You have selected the Reload Knight's Account Option.\n");
        Reload(&balance);
        break;
    case 114:
        printf("You have selected the Reload Knight's Account Option.\n");
        Reload(&balance);
        break;

    //exits the function
    case 69:
        printf("***********************************************************************************\n");
        printf("You are now exiting the application!\n");
        printf("You have $%0.2f in your account.\n", balance);
        printf("Have a great day!\n");
        printf("Go Knights! Charge On!\n");
        printf("***********************************************************************************\n");
        break;
    case 101:
        option = 69; //sets option equal to the exit value
        printf("***********************************************************************************\n");
        printf("You are now exiting the application!\n");
        printf("You have $%0.2f in your account.\n", balance);
        printf("Have a great day!\n");
        printf("Go Knights! Charge On!\n");
        printf("***********************************************************************************\n");
        break;
    }
    }while(option != 69); //function will end when option = 69

    return 0;
}

void Greeting() //welcomes the user to the program
{
    printf("***********************************************************************************\n");
    printf("Welcome to UCF's Knight's Account Services!");
    printf("This is where you will manage your Knight's Account for all\n");
    printf("purchasing needs at UCF!\n");
    printf("How can we help you today?\n");
    printf("***********************************************************************************\n");
}

void DisplayMenu() //shows the food options at the Knightstop
{
    printf("You selected the Browse option.\n");
    printf("***********************************************************************************\n");
    printf("Here is What we have in stock today!\n"); //cost of menu items
    printf("1.   Bottled Water              $1.00\n");
    printf("2.   Chips                      $2.13\n");
    printf("3.   Soda                       $1.12\n");
    printf("4.   Wrap                       $5.31\n");
    printf("5.   Candy Bag                  $3.21\n");
    printf("6.   Cheese Pizza               $6.24\n");
    printf("7.   Chocolate Brownie          $1.23\n");
    printf("8.   Chocolate Chip Cookie      $1.15\n");
    printf("***********************************************************************************\n");
}

void Purchase(double *balance) //user can purchase food at Knightstop
{

    printf("You selected the Purchase option.\n");
    printf("***********************************************************************************\n");
    printf("Here is What we have in stock today!\n"); //cost of menu items
    printf("1.   Bottled Water              $1.00\n");
    printf("2.   Chips                      $2.13\n");
    printf("3.   Soda                       $1.12\n");
    printf("4.   Wrap                       $5.31\n");
    printf("5.   Candy Bag                  $3.21\n");
    printf("6.   Cheese Pizza               $6.24\n");
    printf("7.   Chocolate Brownie          $1.23\n");
    printf("8.   Chocolate Chip Cookie      $1.15\n");
    printf("***********************************************************************************\n");
    printf("What would you like today?\n");

    int item; //user inputs item they want to purchase
    printf("Option selected: ");
    scanf("%d", &item);
    printf("\n");
    double price;

    switch(item) //switch statements determine the cost of the menu item
    {
        case 1:
           price = 1.00;
           printf("Bottled water has been selected. Total cost is: $1.00\n");
           Transaction(balance, price);
           break;

        case 2:
           price = 2.13;
           printf("Chips has been selected. Total cost is: $2.13\n");
           Transaction(balance, price);
           break;

        case 3:
           price = 1.12;
           printf("Soda has been selected. Total cost is: $1.12\n");
           Transaction(balance, price);
           break;

        case 4:
           price = 5.31;
           printf("Wrap has been selected. Total cost is: $5.31\n");
           Transaction(balance, price);
           break;

        case 5:
           price = 3.21;
           printf("Candy bag has been selected. Total cost is: $3.21\n");
           Transaction(balance, price);
           break;

        case 6:
           price = 6.24;
           printf("Cheese Pizza has been selected. Total cost is: $6.42\n");
           Transaction(balance, price);
           break;

        case 7:
           price = 1.23;
           printf("Chocolate Brownie has been selected. Total cost is: $1.23\n");
           Transaction(balance, price);
           break;

        case 8:
           price = 1.15;
           printf("Chocolate Chip Cookie has been selected. Total cost is: $1.15\n");
           Transaction(balance, price);
           break;

        default:
            printf("You did not select a valid option.\n");
    }
}

void ViewAccount(double *balance) //displays amount of money on Knight's Account
{
    printf("You selected View Account Summary Option.\n");
    printf("***********************************************************************************\n");
    printf("Here is your current Knight's Account balance.\n");
    printf("You have $%0.2f in your account.\n", *balance);
    printf("***********************************************************************************\n");
}

void Transaction(double *balance, double price) //subtracts price from Knight's account
{
    printf("Beginning Transaction Process.\n");

    if(*balance < price) //when user does not have enough money
        {
            while(*balance < price) //user is prompted to reload until they have enough
            {
            printf("You do not have enough funds in your account.\n");
            printf("Please reload your account balance.\n");
            Reload(balance);
            }
        }

    double leftover = *balance - price; //gives the leftover

    printf("Your account balance before official transaction: $%0.2f\n", *balance);
    printf("Billing $%0.2f to your account.\n", price);
    printf("Transaction was successful!\n");
    printf("You now have $%0.2f in your account balance.\n", leftover);
    printf("***********************************************************************************\n");

    *balance = leftover; //sets account balance to what is leftover from purchase
}

void Reload(double *balance) //users can add money to their account
{
    int select; //user selects amount of money to add
    printf("***********************************************************************************\n");
    printf("How much would you like to reload to your Knight's Account?\n");
    printf("1: $1.00\n");
    printf("2: $5.00\n");
    printf("3: $10.00\n");

    printf("Option selected: "); //user input
    scanf("%d", &select);
    printf("***********************************************************************************\n");

    if(select == 1) //determines how much money user gets
        {printf("$1.00 has been added to your Knight's Account successfully\n");
        *balance = *balance + 1.00;
        }
    else if(select == 2)
        {printf("$5.00 has been added to your Knight's Account successfully\n");
        *balance = *balance + 5.00;
        }
    else if(select == 3)
        {printf("$10.00 has been added to your Knight's Account successfully\n");
        *balance = *balance +10.00;
        }
    else //not a valid option
        printf("Not a valid option\n");

    printf("***********************************************************************************\n");
}
