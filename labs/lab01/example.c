#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void *f(void *arg) {
  	long id = *(long*)arg;

	for (int i = 0; i < 100; i++) {
		printf("%d. Hello World din thread-ul %ld!\n", i, id);
	}
  	
  	pthread_exit(NULL);
}

// EX 4
void *g(void *arg) {
  	printf("ANA ARE MERE!\n");
  	
  	pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
	long NUM_THREADS = sysconf(_SC_NPROCESSORS_CONF);
	printf("%ld CORES AVAILABLE!\n", NUM_THREADS);

	pthread_t threads[NUM_THREADS];
  	int r;
  	long id;
  	void *status;
	long ids[NUM_THREADS];


	// EX 1-3
  	// for (id = 0; id < NUM_THREADS; id++) {
	// 	ids[id] = id; 
	// 	r = pthread_create(&threads[id], NULL, f, &ids[id]);

	// 	if (r) {
	//   		printf("Eroare la crearea thread-ului %ld\n", id);
	//   		exit(-1);
	// 	}
  	// }

	// EX 4
	ids[0] = 0;
	r = pthread_create(&threads[0], NULL, f, &ids[0]);
	if (r) {
		printf("Eroare la crearea thread-ului 0\n");
		exit(-1);
	}

	ids[1] = 1;
	r = pthread_create(&threads[1], NULL, g, &ids[1]);
	if (r) {
		printf("Eroare la crearea thread-ului 1\n");
		exit(-1);
	}


  	for (id = 0; id < NUM_THREADS; id++) {
		r = pthread_join(threads[id], &status);

		if (r) {
	  		printf("Eroare la asteptarea thread-ului %ld\n", id);
	  		exit(-1);
		}
  	}

  	pthread_exit(NULL);
}
