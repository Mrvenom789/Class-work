//Zachary Hull
//Dr. Steinberg
//COP 3223 Section 2
//Large Program 1

#define ROUNDS 5
#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>

void Greeting(); //display welcome message to user
void SetSeed(int seed); //set the seed of pseudo random generator
int PlayRound(char answer, int round); //play one round
char GetGuess(); //retrieve the user's guess
int CompareLetters(char guess, char answer); //compare the user's guess with the answer
void RoundResult(int result, char answer); //determine if the user won or loss the round

int main()
{
    Greeting(); //function call

    SetSeed(3); //function call

    for (int game = 1; game <= ROUNDS; game++) //loop for all rounds
    {
        printf("************************************************\n");

        char answer;
        answer = (rand() % (90 - 65 +1) + 65); //sets parameters for the random number generator

        int value;
        value = PlayRound(answer, game);
        RoundResult(value, answer);\

    }
    printf("************************************************\n");
    printf("The game is over. Thanks for playing!\n"); //the game is over now

    return 0;
}

void Greeting() //definition for the welcome message
{
    printf("************************************************\n");
    printf("Welcome to the Letter Guessing Game!\n");
    printf("Try to guess the letter I am thinking about. You get 5 chances.\n");
    printf("You get to play 5 rounds in total. Ready? Let's go!\n");
    printf("************************************************\n");
}

void SetSeed(int seed) //sets variable for random number generator
{
    srand(seed);
}

int PlayRound(char answer, int round)
{
    printf("Round %d\n", round); //round number

    for (int chance = 1; chance <= 5; chance ++) //loop for individual rounds
    {
        printf("Guess #%d\n", chance);
        char guess;
        guess = GetGuess(); //function call

        if(guess >= 97 && guess <= 122) //for when users input lower-case letters
            guess = guess - 32;

        int store = CompareLetters(guess, answer); //function call
        if(store == 1) //correct or incorrect answers
            {
                printf("You guessed correctly.\n");
                return 1;
            }
        else
            {
                printf("You guessed incorrectly.\n");
            }

    }
    return 0;
}

char GetGuess()
{
    char guess; //users input their guess
    printf("Enter your guess: ");
    scanf(" %c", &guess);
    return guess;
}

int CompareLetters(char guess, char answer) //determines whether answer comes before or after guess
{


   if(guess > answer)
    {
        printf("Your answer comes after the letter I am thinking of.\n");
        return 2;
    }
   else if(guess < answer)
    {
        printf("Your answer comes before the letter I am thinking of.\n");
        return 0;
    }
   else if(guess == answer)
    {
        printf("Your answer is correct. Congratulations.\n");
        return 1;
    }

}
void RoundResult(int result, char answer) //correct or incorrect message
{

    if(result == 1)
        printf("Congratulations, you have guessed correctly!\n");
    else
        printf("Sorry, you have run out of guesses. The correct answer is: %c\n", answer);


}
