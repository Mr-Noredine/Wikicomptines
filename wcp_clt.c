/**********************

	 Noureddine		MOHAMMEDI 		12209923
	 
	-> Je déclare qu'il s'agit de mon propre travail.
	-> Ce travail a été réalisé intégralement par un être humain. 
	

**********************************************************************************/
/* fichiers de la bibliothèque standard */
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

#define PORT_WCP 4321

void usage(char *nom_prog)
{
	fprintf(stderr, "Usage: %s addr_ipv4\n"
			"client pour WCP (Wikicomptine Protocol)\n"
			"Exemple: %s 208.97.177.124\n", nom_prog, nom_prog);
}

/** Retourne (en cas de succès) le descripteur de fichier d'une socket
 *  TCP/IPv4 connectée au processus écoutant sur port sur la machine d'adresse
 *  addr_ipv4 */
int creer_connecter_sock(char *addr_ipv4, uint16_t port);

/** Lit la liste numérotée des comptines dans le descripteur fd et les affiche
 *  sur le terminal.
 *  retourne : le nombre de comptines disponibles */
uint16_t recevoir_liste_comptines(int fd);

/** Demande à l'utilisateur un nombre entre 0 (compris) et nc (non compris)
 *  et retourne la valeur saisie. */
uint16_t saisir_num_comptine(uint16_t nb_comptines);

/** Écrit l'entier ic dans le fichier de descripteur fd en network byte order */
void envoyer_num_comptine(int fd, uint16_t nc);

/** Affiche la comptine arrivant dans fd sur le terminal */
void afficher_comptine(int fd);

int main(int argc, char *argv[])
{
	if (argc != 2) {
		usage(argv[0]);
		return 1;
	}

	int sock = creer_connecter_sock(argv[1], PORT_WCP);
	
	uint16_t nb_comptines = recevoir_liste_comptines(sock); 
	uint16_t nc = saisir_num_comptine(nb_comptines);
	envoyer_num_comptine(sock, nc);
	afficher_comptine(sock);
	
	close(sock);
	return 0;
}

int creer_connecter_sock(char *addr_ipv4, uint16_t port)
{
	
	int sock = socket(AF_INET, SOCK_STREAM, 0);
	if (sock < 0) {
		perror("socket");
		exit(3);
	}
	
	struct sockaddr_in sa = { .sin_family = AF_INET, 
								.sin_port = ntohs(PORT_WCP)
							};
	if (inet_pton(AF_INET, addr_ipv4, &sa.sin_addr) == -1) {
		perror("inet_pton");
		exit(4);
	}
	
	socklen_t sl = sizeof(sa);
	int status = connect(sock, (struct sockaddr*) &sa, sl);
	if (status < 0) {
		perror("connect");
		exit(4);
	}

	return sock;
}

uint16_t recevoir_liste_comptines(int fd)
{	

	uint16_t nb_comptines = 0;
	char buffer[257];
	read(fd, buffer, 2);
	sscanf(buffer,"%"SCNu16"\n", &nb_comptines);	
	
	for(int i = 0; i <= nb_comptines + 1 ; i++) {
		read_until_nl(fd, buffer);
		printf("%s", buffer);
	}
	
	return nb_comptines;
}

uint16_t saisir_num_comptine(uint16_t nb_comptines)
{
	uint16_t n;
	printf("Quelle comptine voulez-vous ? (Etrer un entier entre 0 et %"PRIu16") : ", nb_comptines);
	do {
		scanf("%"SCNu16, &n);
	} while(n >= nb_comptines || n < 0);
	
	return n;
}

void envoyer_num_comptine(int fd, uint16_t nc)
{
	nc = htons(nc);
	uint8_t * buffer = malloc(3);
	
	memcpy(buffer, &nc, 2);
	ssize_t status = send(fd, buffer, 2, 0);
	if (status < 0) {
		perror("send");
		exit(4);
	}
	free(buffer);
}



int est_ligne_vide(char * ligne) {
	if(ligne[0] == '\n')	return 1;
	return 0;
}

void afficher_comptine(int fd)
{
	char buffer[1024];
	int n, cpt_ligneVide = 0;
	while((n = read_until_nl(fd, buffer)) != 0) {
		
		buffer[n+1] = '\0';
		if (est_ligne_vide(buffer)) {
			cpt_ligneVide++;
			if (cpt_ligneVide == 2) return;
			printf("\n");
			continue;
		}
		
		cpt_ligneVide = 0;
		write(0, buffer, n);
	}
	
}
