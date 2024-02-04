#include <stdio.h>
#include <string.h>
#define SIZE 20
#define STRIKES 6

void rules(void); //display rules of the game
void maskWord (char starword[], int size); //mask the word with stars to display to user in game
int playRound(char starword[], char answer[]); //function that plays a round of hangman. returns 1 if user won
int occurancesInWord(char userguess, char answer[]); //number of times letter occurs in word. Greater than 0 means letter exist
void updateStarWord(char starword[], char answer[], char userguess); //update starword. replace respective * character with respective letter
void playAgain(int *play); //ask user if to play again. 1 is yes 2 is no

int main()
{
    FILE *inp; //opens the text file
    inp = fopen("words.txt", "r");

    char starword[SIZE];
    char userguess;
    char answer[SIZE];
    int play;
    int input_status;
    //double item;


    printf("Welcome to the Hangman Game!\n");
    printf("***************************************************\n");

    rules();

    do
    {
        input_status = fscanf(inp, "%s", answer); //scans words from the text file

        if(input_status == EOF) //stops looping when file reaches last word
        {
            break;
        }
        strcpy(starword, answer); //copies word to starword function

        int size = strlen(starword); //determines the size of the two strings
        int sizeA = strlen(answer);

        maskWord(starword, size); //function call

        playRound(starword, answer); //function call

        playAgain(&play); //function call

    }
    while(play == 1);

    printf("***************************************************\n");

    if(input_status == EOF)
        printf("Sorry, there are no more words left.\n");

    printf("Thank you for playing today!\n");

    fclose(inp); //closes the file

    return 0;
}

void rules()
{
    //print statements explain the rules of the game
    printf("Here are the rules.\n");
    printf("I will provide you a set of * characters for each round.\n");
    printf("Each * represents a letter in the English Alphabet.\n");
    printf("You must figure out each letter of the missing word.\n");
    printf("For every correct letter guessed, I will reveal its place in the word.\n");
    printf("Each mistake will result in a strike.\n");
    printf("6 strikes will result in a loss that round.\n");
    printf("Are you ready? Here we go!\n");
}

void maskWord (char starword[], int size)
{
    for(int i = 0; i < size; i++)
    {
        starword[i] = '*'; //replaces letters with the stars
    }
}

int playRound(char starword[], char answer[])
{
    printf("***************************************************\n");
    printf("Welcome to the Round!\n");

    //defines variables for this function
    int strike = 0;
    char guesses[SIZE];
    int index = 0;
    int game = 2;

    while(game > 1){

        char userguess;

        printf("You currently have %d strike(s).\n", strike);

        printf("%s\n", starword);

        printf("Enter your guess: ");
        scanf(" %c", &userguess);

        if(userguess < 97) //changes capital letters to lowercase
            {
                userguess += 32;
            }

        int counter = occurancesInWord(userguess, answer); //function call

        if(counter > 0) //lets user know if letter is in the answer
            printf("The letter %c is in the word.\n", userguess);
        else //letter is not in the answer
            {
                printf("The letter %c is NOT in the word.\n", userguess);
                strike ++;;
            }

        updateStarWord(starword, answer, userguess); //function call

        guesses[index] = userguess; //keeps track of guessed letters
        guesses[index + 1] = '\0';
        index++;

        printf("Letter(s) you have guessed: %s\n", guesses); //displays all letters that have been guessed

        if(strcmp(starword, answer) == 0) //game loops until user correctly guesses the word
            {
                game = 1;
                printf("Congratulations! You Won! The word was %s.\n", answer);
                printf("***************************************************\n");
            }

        if(strike >= STRIKES) //game loops until user gets too many wrong
            {
                game = 0;
                printf("Sorry, you did not win the round. The word was %s.\n", answer);
                printf("***************************************************\n");
            }
    }
        return game;
}

int occurancesInWord(char userguess, char answer[])
{
    int sizeA = strlen(answer); //length of answer string
    int counter = 0;
    for(int j = 0; j < sizeA; ++j)
    {
        if(answer[j] == userguess)
            {
                counter++; //updates when user guesses correctly
            }
    }
    return counter;
}

void updateStarWord(char starword[], char answer[], char userguess)
{
    int answer_len = strlen(answer); //length of answer string
    for(int i = 0; i < answer_len; i++) {
        if(answer[i] == userguess) {
            starword[i] = userguess; //replaces stars with correct guesses
        }
    }
}

void playAgain(int *play)
{
    //asks user if they would like to play again
    printf("Would you like to play again?\n");
    printf("1: Yes\n");
    printf("2: No\n");
    printf("Choice: ");
    scanf("%d", play);
}
