//Zachary Hull
//Dr. Steinberg
//COP3223 section 2
//Large program 4

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAXSIZE 30

typedef struct{ //record of fighter from Super Smash Bros. Ultimate
    char name[MAXSIZE];
    char fighterClass[MAXSIZE];
    char fromGame[MAXSIZE];
    int fighterNumber;
} character_t;

char options(char select); //displays options for the program
int sixrecords(character_t array[], int size); //add elements to array
void insert(character_t *array, int *size); //inserts user inputs into array
void display(character_t *array, int size); //displays all elements
void search(character_t *array, int size); //searches for elements in array
int removeCharacter(character_t *array, int size);//removes elements

int main()
{
    FILE *outp; //opens output text file
    outp = fopen("records.txt", "w");

    printf("Welcome to the Super Smash Bros. Ultimate Fighter Record System!\n"); //Welcomes user to program
    printf("*************************************************************************\n");

    int end; //value determines when loops end
    char select;
    int size = 0;

    character_t array[MAXSIZE];
    size = sixrecords(array, size); //function call

    while(end != 1){
    char choice = options(select); //function call

    switch(choice)
    {
    case 'A': //add to the array
        insert(array, &size); //function call
        break;

    case 'a':
        insert(array, &size);
        break;

    case 'R': //remove from the array
        size = removeCharacter(array, size); //function call
        break;

    case 'r':
        size = removeCharacter(array, size);
        break;

    case 'D': //display all elements of the array
        display(array, size); //function call
        break;

    case 'd':
        display(array, size);
        break;

    case 'L': //search for the array
        search(array, size); //function call
        break;

    case 'l':
        search(array, size);
        break;

    case 'E':
        end = 1; //loop ends
        break;

    case 'e':
        end = 1;
        break;

    default: //When user input is not valid
        printf("Invalid input. Please select again.\n");
    }
    }
    printf("Exiting the program and saving records to a text file called records.txt\n");

    int i = 0;
    for(i; i < size; i++) //loop to print values in the text file
    {
        int ID = i + 1;
        fprintf(outp, "ID:               %d\n", ID);
        fprintf(outp, "Character Name:   %s\n", array[i].name);
        fprintf(outp, "Fighter Class:    %s\n", array[i].fighterClass);
        fprintf(outp, "From the Game:    %s\n", array[i].fromGame);
        fprintf(outp, "Roster Number:    %d\n", array[i].fighterNumber);
        fprintf(outp, "+++++++++++++++++++++++++++++++++++++++++++++++\n");
    }

    fclose(outp); //closes output text file
    return 0;
}

char options(char select) //displays all options for the program
{
    printf("What would you like to do?\n");
    printf("A: Add character record\n");
    printf("R: Remove character record\n");
    printf("D: Display all character records\n");
    printf("L: Lookup character record\n");
    printf("E: Exit program\n");
    printf("Enter Program: ");
    scanf(" %c", &select); //user input
    printf("*************************************************************************\n");

    return select;
}
int sixrecords(character_t array[], int size) //adds 6 records to the array
{
    strcpy(array[0].name, "Mario");
    strcpy(array[0].fighterClass, "Brawler");
    strcpy(array[0].fromGame, "Super_Mario_Bros.");
    array[0].fighterNumber = 1;
    ++size;

    strcpy(array[1].name, "Sonic");
    strcpy(array[1].fighterClass, "Brawler");
    strcpy(array[1].fromGame, "Sonic_the_Hedgehog");
    array[1].fighterNumber = 38;
    ++size;

    strcpy(array[2].name, "Samus");
    strcpy(array[2].fighterClass, "Gunner");
    strcpy(array[2].fromGame, "Metroid");
    array[2].fighterNumber = 4;
    ++size;

    strcpy(array[3].name, "Cloud");
    strcpy(array[3].fighterClass, "Swordfighter");
    strcpy(array[3].fromGame, "Final_Fantasy_VII");
    array[3].fighterNumber = 61;
    ++size;

    strcpy(array[4].name, "Joker");
    strcpy(array[4].fighterClass, "Sword_fighter");
    strcpy(array[4].fromGame, "Persona_5");
    array[4].fighterNumber = 71;
    ++size;

    strcpy(array[5].name, "Sora");
    strcpy(array[5].fighterClass, "Sword_fighter");
    strcpy(array[5].fromGame, "Kingdom_Hearts");
    array[5].fighterNumber = 82;
    ++size;

    return size;
}

void insert(character_t *array, int *size)
{
    int fclass; //variable to determine fighter class

    printf("Adding a new fighter to the record.\n");
    printf("When multiple words are required, please use an underscore, not a space.\n"); //spaces cannot be used

    if(*size == MAXSIZE)
    {
        printf("Sorry, there is no more room to record anything. Try making some space.");
    }
    printf("Please enter the fighter's name: "); //user inputs fighter name
    scanf("%s", array[*size].name);

    printf("Please select the fighter's class:\n"); //user inputs number to determine fighter class
    printf("1. Brawler\n");
    printf("2. Swordfighter\n");
    printf("3. Gunner\n");
    printf("Select option: ");
    scanf("%d", &fclass);

    if(fclass == 1) //if statements to determine class of the fighter
    {
        printf("Brawler selected.\n");
        strcpy(array[*size].fighterClass, "Brawler");
    }
    if(fclass == 2)
    {
        printf("Swordfighter selected.\n");
        strcpy(array[*size].fighterClass, "Swordfighter");
    }
    if(fclass == 3)
    {
        printf("Gunner selected.\n");
        strcpy(array[*size].fighterClass, "Gunner");
    }

    printf("Please enter what game they are from: "); //user inputs the fighter's game of origin
    scanf("%s", array[*size].fromGame);

    printf("Please Enter their roster number: "); //User inputs the roster number for the fighter
    scanf("%d", &array[*size].fighterNumber);
    ++*size;

    printf("*************************************************************************\n");
}

int removeCharacter(character_t *array, int size)
{
    char removeN[MAXSIZE];
    char removeG[MAXSIZE];

    printf("Removing fighter from the record.\n"); //user selects fighter record to remove
    printf("Enter Fighter's Name (use underscore instead of spaces): ");
    scanf("%s", removeN);

    printf("Enter the name of the game the fighter is from: "); //user inputs game of that fighter
    scanf("%s", removeG);

    int nameCmp; //strings to compare to existing records
    int gameCmp;

    int j = 0;
    for(j; j < size; j++) //for loop goes through the arrays
    {
        nameCmp = strcmp(removeN, array[j].name); //compares user inputs to fighters in the record
        gameCmp = strcmp(removeG, array[j].fromGame);

        if(nameCmp == 0 && gameCmp == 0)
            break;
    }
    if(nameCmp == 0 && gameCmp == 0)
    {
       for(int k = j; k < size-1; k++) //all array values are shifted over to remove chosen record
    {
        strcpy(array[k].name, array[k+1].name);
        strcpy(array[k].fighterClass, array[k+1].fighterClass);
        strcpy(array[k].fromGame, array[k+1].fromGame);
        array[k].fighterNumber = array[k+1].fighterNumber;
    }
    size -= 1; //size is decremented to prevent copying values
    }
    else
        printf("There is no record of that fighter.\n");;

    printf("*************************************************************************\n");
    return size;
}

void display(character_t *array, int size)
{
    int i = 0;
    printf("Displaying all fighter records.\n");
    for(i; i < size; i++) //loop will display all elements that have been recorded
    {
        int ID = i + 1; //ID represents number in the record
        printf("ID:               %d\n", ID);
        printf("Character Name:   %s\n", array[i].name);
        printf("Fighter Class:    %s\n", array[i].fighterClass);
        printf("From the Game:    %s\n", array[i].fromGame);
        printf("Roster Number:    %d\n", array[i].fighterNumber);
        printf("+++++++++++++++++++++++++++++++++++++++++++++++\n");
    }
    printf("*************************************************************************\n");
}

void search(character_t *array, int size)
{
    char searchName[MAXSIZE]; //strings to check if fighter and game is found in record
    char searchGame[MAXSIZE];

    printf("Search for fighter records\n");
    printf("Enter the fighter's name (use underscore instead of space bar, if needed): ");
    scanf("%s", searchName); //user inputs fighter name

    printf("Enter the game the fighter is from: ");
    scanf("%s", searchGame); //user inputs gamer of origin

    int nameCmp2; //comparison variables
    int gameCmp2;
    int h = 0;

    for(h; h < size; h++) //loop goes through the array
    {
        nameCmp2 = strcmp(searchName, array[h].name); //used to compare user inputs to compare with records
        gameCmp2 = strcmp(searchGame, array[h].fromGame);

        if(nameCmp2 == 0 && gameCmp2 == 0)
            break;
    }

    if(nameCmp2 == 0 && gameCmp2 == 0) //if strcmp returns 0, string is found in record
        {
            int ID = h + 1;
            printf("Here is the fighter's info:\n"); //record is displayed
            printf("+++++++++++++++++++++++++++++++++++++++++++++++\n");
            printf("ID:               %d\n", ID);
            printf("Character Name:   %s\n", array[h].name);
            printf("Fighter Class:    %s\n", array[h].fighterClass);
            printf("From the Game:    %s\n", array[h].fromGame);
            printf("Roster Number:    %d\n", array[h].fighterNumber);
            printf("+++++++++++++++++++++++++++++++++++++++++++++++\n");
        }

    else //displays if strcmp does not return 0
        printf("There is no record of that fighter.\n");
    printf("*************************************************************************\n");
}
