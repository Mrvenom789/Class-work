//Dr. Andrew Steinberg
//COP3502 Computer Science 1
//Programming Assignment 5 Solution

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#define MAX 2000

typedef struct{
	char * name; //dynamic string
	int rank;
}player_t;

//function prototype(s)
player_t* scanRoster(player_t *roster);
void mergeSort(player_t *roster, int a, int c); //sorts the players by rank
void merge(player_t *roster, int a, int b, int c); //sorts the players

int main(void)
{
    int a, b, c;
	int seed;
	printf("Enter seed: ");
	scanf("%d", &seed);
	srand(seed);

	player_t *roster = (player_t*) malloc(sizeof(player_t) * MAX); //sets the size of the roster
	player_t *team1 = (player_t*) malloc(sizeof(player_t) * MAX / 2); //sets the size of the teams
	player_t *team2 = (player_t*) malloc(sizeof(player_t) * MAX / 2);

	roster = scanRoster(roster);

	mergeSort(roster, 0, 1999); //sorts the array


    for(int i = 0; i < 1000; i++) //divides the players into two teams
        team1[i].rank = roster[0 + i].rank;

    for(int j = 0; j < 1000; j++)
        team2[j].rank = roster[1000 + j].rank;

	double average1 = 0;
	double average2 = 0;

	for(int k = 0; k < 1000; k++) //adds the ranks
        average1 += team1[k].rank;

    for(int l = 0; l < 1000; l++)
        average2 += team2[l].rank;

    average1 /= 1000; //divides by size of the arrays
    average2 /= 1000;

	printf("Team 1 Rank Average is: %f\n", average1); //displays averages
	printf("Team 2 Rank Average is: %f\n", average2);

	for(int m = 0; m < 2000; m++) //frees for each name
        free(roster[m].name);

	free(roster); //free the arrays
	free(team1);
	free(team2);

	return 0;
}




player_t* scanRoster(player_t *roster)
{
	FILE *fptr = fopen("players.txt", "r");

	char name[20];
	int index = 0;

	while(fscanf(fptr, "%s", name) == 1)
	{
		roster[index].name = (char *) malloc(sizeof(char) * 20);
		strcpy(roster[index].name, name);
		roster[index].rank = rand() % 5 + 1;
		++index;
	}

	fclose(fptr);

	return roster;
}

void merge(player_t *roster, int a, int b, int c) //sorts the players
{
    int n1 = b - a + 1;
    int n2 = c - b;

    player_t * leftarr = (player_t *) malloc(sizeof(player_t) * n1); //allocate space for the arrays
    player_t * rightarr = (player_t *) malloc(sizeof(player_t) * n2);

    for(int x = 0; x < n1; ++x) //moves through the arrays
        leftarr[x].rank = roster[a + x].rank;

    for(int x = 0; x < n2; ++x)
        rightarr[x].rank = roster[b + x + 1].rank;

    int i = 0;
    int j = 0;
    int k = a;

    //for out of bounds
    while(i < n1 && j < n2)
    {
        if(leftarr[i].rank <= rightarr[j].rank)
        {
            roster[k].rank = leftarr[i].rank;
            i++;
        }
        else
        {
            roster[k].rank = rightarr[j].rank;
            j++;
        }

        k++;
    }

    while(i < n1)
    {
        roster[k].rank = leftarr[i].rank;
        i++;
        k++;
    }

    while(j < n2)
    {
        roster[k].rank = rightarr[j].rank;
        j++;
        k++;
    }

    free(leftarr); //free the arrays
    free(rightarr);
}

void mergeSort(player_t *roster, int a, int c) //sorts the players by rank
{
    if(a < c)
    {
        int b = (c + a) / 2; //recursively calls the arrays
        mergeSort(roster, a, b);
        mergeSort(roster, b + 1, c);
        merge(roster, a, b, c);
    }
}
