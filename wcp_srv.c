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
		
		// lorsque je met envoyer_liste(0, c) il affiche le catalogue dans le terminal mais lorsque je met envoyer_liste(sock, c) je n'arrive meme pas a l'instruction suivante "catalogue bien envoyée.
		envoyer_liste(socket_l, c);
		liberer_catalogue(c);
		
		uint16_t nc = recevoir_num_comptine(socket_l);
		printf("je suis là\n");
		printf("le numero comptine est : %"PRIu16"\n", nc);
	}
	
	
	/* À compléter */
	return 0;
}

int creer_configurer_sock_ecoute(uint16_t port)
{
	/* À définir */
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
	/* À définir */
	uint16_t i = 0;
	// ça marche bien c'est 2 instructions, mais je n'ai pas bien compris leur fonctionnement !
	// ma démarche est de mettre sur la premiere ligne le nombre de comptines, puis sur les lignes suivantes mettre la liste, apres avec la maniere dont j'ai lus les octets et scanner le nombre d'octets sur le buffer avec sscanf dans recevoire_liste_comptines ça marche que lorsque j'ajoute c'est 2 ligne => memcpy (....) et write(fd, buffer, 2);
	
	
	for(i = 0 ; i < c->nb ; i++) {
		dprintf(fd, "\t%"PRIu16" ", i);
		write(fd, c->tab[i]->titre, strlen(c->tab[i]->titre));
		
	}	
		write(fd, "\n" , 1);
	printf("envoyer liste terminé\n");
}


uint16_t recevoir_num_comptine(int fd)
{
	uint16_t nc;
	ssize_t status = recv(fd, &nc, 2, 0);
	if (status < 0) {
		perror("recv");
		exit(4);
	}

	nc = ntohs(nc);
	return nc;

}

void envoyer_comptine(int fd, const char *dirname, struct catalogue *c, uint16_t ic)
{
	/* À définir */
}
