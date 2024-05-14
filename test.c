#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <inttypes.h>
/* bibliothèque standard unix */
#include <unistd.h> /* close, read, write */
#include <sys/types.h>
#include <sys/socket.h>
/* spécifique à internet */
#include <arpa/inet.h> /* inet_pton */
/* spécifique aux comptines */
#include "comptine_utils.h"

void envoyer_num_comptine(int fd, uint16_t nc);

int main() {
	
	envoyer_num_comptine(0, 15);
	
	
	return 0;
}

void envoyer_num_comptine(int fd, uint16_t nc) {
	uint16_t n = ntohs(nc);
	char * buffer = malloc(3);
	memcpy(buffer, &n, 2);
	buffer[3] = '\0';
	
	write(fd, buffer, 3);
}






