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
#include <fcntl.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <dirent.h>
#include <errno.h>
/* spécifique à internet */
#include <arpa/inet.h> /* inet_pton */
/* spécifique aux comptines */
#include "comptine_utils.h"

#define PORT_WCP 4321

void usage(char *nom_prog)
{
	fprintf(stderr, "Usage: %s repertoire_comptines\n"
			"serveur pour WCP (Wikicomptine Protocol)\n"
			"Exemple: %s comptines\n", nom_prog, nom_prog);
}
/** Retourne en cas de succès le descripteur de fichier d'une socket d'écoute
 *  attachée au port port et à toutes les adresses locales. */
int creer_configurer_sock_ecoute(uint16_t port);

/** Écrit dans le fichier de desripteur fd la liste des comptines présents dans
 *  le catalogue c comme spécifié par le protocole WCP, c'est-à-dire sous la
 *  forme de plusieurs lignes terminées par '\n' :
 *  chaque ligne commence par le numéro de la comptine (son indice dans le
 *  catalogue) commençant à 0, écrit en décimal, sur 6 caractères
 *  suivi d'un espace
 *  puis du titre de la comptine
 *  une ligne vide termine le message */
void envoyer_liste(int fd, struct catalogue *c);

/** Lit dans fd un entier sur 2 octets écrit en network byte order
 *  retourne : cet entier en boutisme machine. */
uint16_t recevoir_num_comptine(int fd);

/** Écrit dans fd la comptine numéro ic du catalogue c dont le fichier est situé
 *  dans le répertoire dirname comme spécifié par le protocole WCP, c'est-à-dire :
 *  chaque ligne du fichier de comptine est écrite avec son '\n' final, y
 *  compris son titre, deux lignes vides terminent le message */
void envoyer_comptine(int fd, const char *dirname, struct catalogue *c, uint16_t ic);

int main(int argc, char *argv[])
{
	if (argc != 2) {
		usage(argv[0]);
		return 1;
	}
	
	int sock = creer_configurer_sock_ecoute(PORT_WCP);
	for(;;) {
		struct sockaddr_in sa_clt;
		socklen_t sl = sizeof (sa_clt);
		
		int socket_l = accept(sock, (struct sockaddr *) &sa_clt, &sl);
		if (socket_l < 0) {
			perror("accept");
			exit(4);
		}
		
		struct catalogue * c = creer_catalogue(argv[1]);
		
		envoyer_liste(socket_l, c);
		uint16_t ic = recevoir_num_comptine(socket_l);
		envoyer_comptine(socket_l, argv[1], c, ic);
		liberer_catalogue(c);
		close(socket_l);
	}
	
	
	
	return 0;
}

int creer_configurer_sock_ecoute(uint16_t port)
{

	int sock = socket(AF_INET, SOCK_STREAM, 0);
	if (sock < 0) {
		perror("socket");
		exit(4);
	}
	
	struct sockaddr_in sa = {	.sin_family = AF_INET, 
								.sin_port = htons(port),
								.sin_addr.s_addr = htonl(INADDR_ANY)
							};
	int opt = 1;
	setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(int));
						
	int status = bind(sock, (struct sockaddr *) &sa, sizeof(sa));
	if (status < 0) {
		perror("bind");
		exit(4);
	}
	
	if (listen(sock, 128) < 0) {
		perror("listen");
		exit(2);
	}
	
	return sock;
}

void envoyer_liste(int fd, struct catalogue *c)
{

	uint16_t i = 0;	
	dprintf(fd,"%"PRIu16"\n", c->nb);
	for(i = 0 ; i < c->nb ; i++) {
		dprintf(fd, "\t%"PRIu16" %s", i, c->tab[i]->titre);
	}	
	dprintf(fd, "\n");

}


uint16_t recevoir_num_comptine(int fd)
{
	uint16_t nc;
	uint8_t * buffer = malloc(3);
	ssize_t status = read(fd, buffer, 2);
	if (status < 0) {
		perror("write");
		exit(4);
	}
	memcpy(&nc, buffer, 2);
	nc = ntohs(nc);
	return nc;

}

void envoyer_comptine(int fd, const char *dirname, struct catalogue *c, uint16_t ic)
{
	int n, totale = 0;
	char buffer[256];
	char file_path[256];
	strcpy(file_path, dirname);
	strcat(file_path, "/");
	strcat(file_path, c->tab[ic]->nom_fichier);
	
	int fileD = open(file_path, O_RDONLY);
	if (fileD < 0) {
		perror("open");
		exit(4);
	}
	while ((n = read_until_nl(fileD, buffer)) > 0){
		write(fd, buffer, n);
		totale+= n;
	}
	dprintf(fd, "\n\n");
		 	
}
