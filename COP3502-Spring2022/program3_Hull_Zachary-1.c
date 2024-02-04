//Name: Zachary Hull
//Dr. Steinberg
//COP3502 Spring 2022
//Programming Assignment 3 Skeleton

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

typedef struct card_s{
	int rank;    //number
	char * type; //type of card
	struct card_s * nextptr;
}card_t;

//function prototypes
void rules(); //display rules
int playRound(); //simulate round
card_t * openCardDeck(); //open the card deck and place into a linkedlist setup
card_t * insertBackSetup(card_t *node, char *name, int cardrank); //take card from orginial deck and place in back of linked list for setup of game
int empty(card_t * node); //check to see if linked list is empty
void cleanUp(card_t * head); //free memory to prevent memory leaks
int deckSize(card_t * head); //count number of nodes in the linked list
card_t * search(card_t * node, int spot); //search list for a specific spot in the card deck indexing is similar to array setup
card_t * copyCard(card_t * node); //make a deep copy of card
card_t * removeCard(card_t * node, int spot); //remove card from linkedlist
card_t * insertBackDeck(card_t *head, card_t *node); //place card at end of pile
int compareCard(card_t * cardp1, card_t * cardp2); //compare cards
card_t * moveCardBack(card_t *head); //place card at top of deck to the bottom of the deck

int main()
{
	int seed;
	printf("Enter Seed: ");
	scanf("%d", &seed);

	srand(seed); //seed set
	rules();

	int player; //1 or 2
	int result;

	printf("Would you like to be player 1 or 2?\n");
	printf("Enter 1 or 2: ");
	scanf("%d", &player); //choose player

	for(int game = 1; game <= 5; ++game) //simulate games
	{
		printf("Alright lets play game %d.\n", game);
		printf("Lets split the deck.\n");

		result = playRound(); //play game

		if((result == 1 && player == 1) || (result == 2 && player == 2)) //determine who won
			printf("You won game %d.\n", game);
		else
			printf("I won game %d.\n", game);
	}

	return 0;
}

void rules()
{
	printf("Welcome to the card game war!\n");
	printf("Here are the rules.\n");
	printf("You have a pile of cards and I have a pile of cards.\n");
	printf("We will each pull the top card off of our respective deck and compare them.\n");
	printf("The card with the highest number will win the round and take both cards.\n");
	printf("However if there is a tie, then we have to we have to place one card faced down and the next one faced up and compare the results.\n");
	printf("Winner of the tie, will grab all the cards out. However if it's a tie again, then we repeat the same action.\n");
	printf("Ready? Here we go!\n");
}

card_t * openCardDeck()
{
	FILE *fptr = fopen("deck.txt", "r");

	char *name = (char *) malloc(sizeof(char) * 20);

	if (name == NULL)
	{
		printf("Error in malloc...\n");
		exit(1);
	}

	card_t * head = NULL;

	int cardrank = 13;
	int tracker = 1;
	while(fscanf(fptr, "%s", name) == 1)
	{
		strcat(name, " ");

		if(tracker % 5 == 1)
		{
			strcat(name, "of Clubs");
			head = insertBackSetup(head, name, cardrank);
		}
		else if(tracker % 5 == 2)
		{
			strcat(name, "of Diamonds");
			head = insertBackSetup(head, name, cardrank);
		}
		else if(tracker % 5 == 3)
		{
			strcat(name, "of Hearts");
			head = insertBackSetup(head, name, cardrank);
		}
		else if(tracker % 5 == 4)
		{
			strcat(name, "of Spades");
			head = insertBackSetup(head, name, cardrank);
			tracker = 0;
			--cardrank;
		}

		++tracker;

	}

	free(name);
	fclose(fptr);

	return head;
}

card_t * insertBackSetup(card_t *node, char *name, int cardrank)
{

    if(empty(node)) //check to see if list is empty
    {
		node = (card_t *) malloc(sizeof(card_t));

		if(empty(node)) //check to see if malloc worked
		{
			printf("Did not allocate head successfully...");
			printf("Program Terminating...\n");
			exit(1);
		}
		else //otherwise populate data of new head node
		{
			node->type = (char *) malloc(sizeof(char) * 20);

			if(node->type == NULL)
			{
				printf("Error with malloc...\n");
				exit(1);
			}

			strcpy(node->type, name);
			node->rank = cardrank;
			node->nextptr = NULL; //must make new tail nextptr NULL!!!

			return node;  //terminate
		}

    }

	//here we know that the list has at least one node

	card_t *head = node; //keep pointer to head since we will need to return this address

	while(node->nextptr != NULL) //traverse to tail
		node = node->nextptr;

	node->nextptr = (card_t *) malloc(sizeof(card_t));

	if(node->nextptr == NULL) //see if new tail was allocated successfully
	{
		printf("Did not allocate node successfully...");
		return head; //terminate if tail didn't get created
	}

	//populate new node
	node->nextptr->type = (char *) malloc(sizeof(char) * 20);

	if(node->nextptr->type == NULL)
	{
		printf("Error with malloc...\n");
		exit(1);
	}

	strcpy(node->nextptr->type, name);
	node->nextptr->rank = cardrank;
	node->nextptr->nextptr = NULL; //very important

	return head; //return head node since we need our starting point of the linked list
}

int empty(card_t * node)
{
	return (node == NULL); //return condition result
}

void cleanUp(card_t * head) //free memory to prevent memory leaks
{
    card_t * temp = NULL;

    while(head != NULL) //traverses linked list and frees each node
    {
        temp = head;
        head = head->nextptr;
        free(temp->type);
        free(temp);
    }
}
int deckSize(card_t * head) //count number of nodes in the linked list
{
    int size = 0;

    while(head != NULL)
    {
        head = head -> nextptr;
        size++; //increases with each iteration
    }
    return size;
}
card_t * search(card_t * node, int spot) //search list for a specific spot in the card deck indexing is similar to array setup
{
    int x = 0;
    while(x != spot && node != NULL)
    {
        node = node->nextptr; //iterates for every node in the linked list
        ++x;
    }
    return node;
}

card_t * copyCard(card_t * node) //make a deep copy of card
{
    card_t * card = (card_t*) malloc(sizeof(card_t));
    card->type = (char*) malloc(sizeof(char) * 20);

    strcpy(card->type, node->type); //copies the type of card
    card->rank = node->rank; //copies the number of the card
    card->nextptr = NULL; //sets the next pointer to NULL
    return card;
}

card_t * removeCard(card_t * node, int spot) //remove card from linkedlist
{
    card_t * head = node;
    card_t * temp = search(node, spot);

    if(head == temp)//head is the selected node
    {
        temp = head;
        head = head->nextptr;
        // dont forget to free
        free(temp->type);
        free(temp);
        return head;
    }


    while(node->nextptr != temp)//node after head
    {
        node = node->nextptr;
    }

    node->nextptr = temp->nextptr;
    free(temp->type); //frees the node
    free(temp);

    return head;
}

card_t * insertBackDeck(card_t *head, card_t *node) //place card at end of pile
{
    if(head == NULL) //head is null
        return node;

    else //when head is not null
        {
            head->nextptr = insertBackDeck(head->nextptr, node);
            return head;
        }
}

int compareCard(card_t * cardp1, card_t * cardp2) //compare cards
{
    if(cardp1->rank > cardp2->rank) //card 1 greater than card 2
        return 1;

    if(cardp1->rank < cardp2->rank) //card 2 greater than card 1
        return 2;

    if(cardp1->rank == cardp2->rank) //both cards equal
        return 0;

}

card_t * moveCardBack(card_t *head) //place card at top of deck to the bottom of the deck
{
    if(head == NULL) //head is null
        return NULL;

    if(head->nextptr == NULL) //last node in the linked list
       return head;

    card_t * temp;
    temp = head;
    card_t *newFront;
    newFront = head->nextptr; //newFront becomes new head

    while(temp->nextptr != NULL) //traverses linked list
        temp = temp->nextptr;

    temp->nextptr = head; //temp is new head
    head->nextptr = NULL; //head points to null

    return newFront;
}

int playRound() //simulate round
{
    card_t * deck = openCardDeck();
    card_t * playerOne = NULL;
    card_t * playerTwo = NULL;
    card_t * warDeck = NULL;

    while(deckSize(deck) != 0) //randomizes cards into two decks
    {
        int random = rand() % deckSize(deck);

        playerTwo = insertBackDeck(playerTwo, copyCard(search(deck, random)));
        deck = removeCard(deck, random);

        int random2 = rand() % deckSize(deck);

        playerOne = insertBackDeck(playerOne, copyCard(search(deck, random2)));
        deck = removeCard(deck, random2);
    }

    printf("There are 52 cards in the deck.\n");

    while(playerOne != NULL && playerTwo != NULL)
    {
        card_t * temp = NULL;

        printf("Player 1 pulled out %s. \t Player 2 pulled out %s.\n", playerOne->type, playerTwo->type); //displays the cards drawn by both players

        int compare; //top cards are compared
        compare = compareCard(playerOne, playerTwo);

        if(compare == 1) //when player one wins round
        {
            printf("Player 1 won that round.\n");
            temp = copyCard(playerTwo); //copies card to temp
            playerTwo = removeCard(playerTwo, 0); //removes card from player
            playerOne = moveCardBack(playerOne); //moves front card to back of deck
            playerOne = insertBackDeck(playerOne, copyCard(temp)); //moves new card to back of deck
            printf("Player 1 has %d cards.\n", deckSize(playerOne)); //counts player deck size
            printf("Player 2 has %d cards.\n", deckSize(playerTwo));
        }

        if(compare == 2) //when player two wins round
        {
            printf("Player 2 won that round.\n"); //same as first scenario
            temp = copyCard(playerOne);
            playerOne = removeCard(playerOne, 0);
            playerTwo = moveCardBack(playerTwo);
            playerTwo = insertBackDeck(playerTwo, copyCard(temp));
            printf("Player 1 has %d cards.\n", deckSize(playerOne));
            printf("Player 2 has %d cards.\n", deckSize(playerTwo));

        }

        if(compare == 0) //when round ends in tie
        {
            card_t * copyTemp = NULL;

            if(deckSize(playerOne) == 1)
                return 2;
            if(deckSize(playerTwo) == 1)
                return 1;

            while(playerOne->rank == playerTwo->rank)
            {
                printf("Ugh oh...We have a tie! W-A-R!\n");

                if(deckSize(playerOne) == 2 || deckSize(playerTwo) == 2)
                {
                    copyTemp = copyCard(playerOne); //copies first card of first player
                    playerOne = removeCard(playerOne, 0);
                    warDeck = insertBackDeck(warDeck, copyTemp);

                    copyTemp = copyCard(playerTwo); //copies first card of second player
                    playerTwo = removeCard(playerTwo, 0);
                    warDeck = insertBackDeck(warDeck, copyTemp);

                    printf("Player 1 pulled out %s. \t Player 2 pulled out %s.\n", playerOne->type, playerTwo->type);

                    int compareTie = compareCard(playerOne, playerTwo);

                    if(compareTie == 1)
                    {
                        printf("Player 1 won that W-A-R!\n");

                        while(warDeck != NULL)
                        {
                            playerOne = insertBackDeck(playerOne, copyCard(warDeck));
                            warDeck = removeCard(warDeck, 0);
                        }

                        playerOne = moveCardBack(playerOne); //move first compare card

                        //move second compare card
                        playerOne = insertBackDeck(playerOne, copyCard(playerTwo));
                        playerTwo = removeCard(playerTwo, 0);

                        printf("Player 1 has %d cards.\n", deckSize(playerOne)); //display card numbers
                        printf("Player 2 has %d cards.\n", deckSize(playerTwo));

                    }

                    if(compareTie == 2)
                    {
                        printf("Player 2 won that W-A-R!\n");

                        while(warDeck != NULL)
                        {
                            playerTwo = insertBackDeck(playerTwo, copyCard(warDeck)); //move tie cards
                            warDeck = removeCard(warDeck, 0);
                        }

                        //move first compare card
                        playerTwo = insertBackDeck(playerTwo, copyCard(playerOne));
                        playerOne = removeCard(playerOne, 0);

                        playerTwo = moveCardBack(playerTwo); //move second compare card

                        printf("Player 1 has %d cards.\n", deckSize(playerOne)); //display card numbers
                        printf("Player 2 has %d cards.\n", deckSize(playerTwo));

                    }

                    if(compareTie == 0) //tie within a tie
                    {
                        printf("Player 1 has %d cards.\n", deckSize(playerOne)); //display card numbers
                        printf("Player 2 has %d cards.\n", deckSize(playerTwo));
                    }

                }
                else
                {
                    copyTemp = copyCard(playerOne); //copies first card of first player
                    playerOne = removeCard(playerOne, 0);
                    warDeck = insertBackDeck(warDeck, copyTemp);

                    copyTemp = copyCard(playerOne); //copies second card of first player
                    playerOne = removeCard(playerOne, 0);
                    warDeck = insertBackDeck(warDeck, copyTemp);

                    copyTemp = copyCard(playerTwo); //copies first card of second player
                    playerTwo = removeCard(playerTwo, 0);
                    warDeck = insertBackDeck(warDeck, copyTemp);

                    copyTemp = copyCard(playerTwo); //copies second card of second player
                    playerTwo = removeCard(playerTwo, 0);
                    warDeck = insertBackDeck(warDeck, copyTemp);

                    printf("Player 1 pulled out %s. \t Player 2 pulled out %s.\n", playerOne->type, playerTwo->type);

                    int compareTie = compareCard(playerOne, playerTwo);

                    if(compareTie == 1)
                    {
                        printf("Player 1 won that W-A-R!\n");

                        while(warDeck != NULL)
                        {
                            playerOne = insertBackDeck(playerOne, copyCard(warDeck)); //move tie cards
                            warDeck = removeCard(warDeck, 0);
                        }

                        playerOne = moveCardBack(playerOne); //move first compare card

                        //move second compare card
                        playerOne = insertBackDeck(playerOne, copyCard(playerTwo));
                        playerTwo = removeCard(playerTwo, 0);

                        printf("Player 1 has %d cards.\n", deckSize(playerOne)); //display card numbers
                        printf("Player 2 has %d cards.\n", deckSize(playerTwo));

                        if(playerOne->rank == playerTwo->rank) //for a tie after a W-A-R is settled
                        printf("Player 1 pulled out %s. \t Player 2 pulled out %s.\n", playerOne->type, playerTwo->type);

                    }

                    if(compareTie == 2)
                    {
                        printf("Player 2 won that W-A-R!\n");

                        while(warDeck != NULL)
                        {
                            playerTwo = insertBackDeck(playerTwo, copyCard(warDeck)); //move tie cards
                            warDeck = removeCard(warDeck, 0);
                        }

                        //move first compare card
                        playerTwo = insertBackDeck(playerTwo, copyCard(playerOne));
                        playerOne = removeCard(playerOne, 0);

                        playerTwo = moveCardBack(playerTwo); //move second compare card

                        printf("Player 1 has %d cards.\n", deckSize(playerOne)); //display card numbers
                        printf("Player 2 has %d cards.\n", deckSize(playerTwo));

                        if(playerOne->rank == playerTwo->rank) //for a tie after a W-A-R is settled
                            printf("Player 1 pulled out %s. \t Player 2 pulled out %s.\n", playerOne->type, playerTwo->type);

                    }

                    if(compareTie == 0) //tie within a tie
                    {
                        printf("Player 1 has %d cards.\n", deckSize(playerOne)); //display card numbers
                        printf("Player 2 has %d cards.\n", deckSize(playerTwo));
                    }
                }
            }
        }
        cleanUp(temp);
    }

    if(deckSize(playerOne) == 0)
        {
            cleanUp(playerOne);
            cleanUp(playerTwo);
            cleanUp(deck);
            cleanUp(warDeck);
            return 2;
        }

    if(deckSize(playerTwo) == 0)
        {
            cleanUp(playerTwo);
            cleanUp(playerOne);
            cleanUp(deck);
            cleanUp(warDeck);
            return 1;
        }

}
