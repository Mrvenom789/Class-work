//Name: Zachary Hull
//Dr. Steinberg
//COP3502 Spring 2022
//Programming Assignment 2

#include<stdio.h>
#include<stdlib.h>

int blobDetect(int ** picture, int x, int y, int limit);

int main()
{
	//setup randomness
    int x;

    printf("Enter seed: ");
    scanf("%d", &x);

    srand(x); //set seed

	printf("Enter the size of the picture: ");

	int size;
	scanf("%d", &size);

	int ** picture = (int **) malloc(size * sizeof(int *));

	for(int x = 0; x < size; ++x)
		picture[x] = (int *) malloc(size * sizeof(int));

	for(int test = 1; test <= 5; ++test)
	{
		printf("Test %d\n", test);

		for(int r = 0; r < size; ++r)
			for(int c = 0; c < size; ++c)
				picture[r][c] = rand() % 2; //generate random number between 0 and 1


		int numblobs = 0;

		for(int r = 0; r < size; ++r)
			for(int c = 0; c < size; ++c)
				if(blobDetect(picture, r, c, size) > 0)
					++numblobs;

		printf("There are %d blob(s) in the picture.\n", numblobs);
	}

	//Super important! I don't think I can emphasize how important this is to do!
	for(int x = 0; x < size; ++x)
		free(picture[x]);

	free(picture);

	return 0;
}

int blobDetect(int ** picture, int x, int y, int limit)
{
    if(x < 0 || y < 0 || x >= limit || y >= limit) //if x or y tries to go out of bounds
        return 0;

    if(picture[x][y] == 0) //if there is no blob
        return 0;

    if(picture[x][y] == 1) //when array finds an ink blob
    {   picture[x][y] = 0;
        return 1 + blobDetect(picture, x + 1, y + 1, limit) +
        blobDetect(picture, x - 1, y + 1, limit) +
        blobDetect(picture, x, y + 1, limit) +
        blobDetect(picture, x + 1, y, limit) +
        blobDetect(picture, x - 1, y, limit) +
        blobDetect(picture, x + 1, y - 1, limit) +
        blobDetect(picture, x, y - 1, limit) +
        blobDetect(picture, x - 1, y - 1, limit);
    }
}
