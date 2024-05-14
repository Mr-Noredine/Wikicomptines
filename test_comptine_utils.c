/**********************

	 Noureddine		MOHAMMEDI 		12209923
	 
	-> Je déclare qu'il s'agit de mon propre travail.
	-> Ce travail a été réalisé intégralement par un être humain. 
	

**********************************************************************************/


#include <stdlib.h>
#include <stdio.h>
#include <inttypes.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>
#include "comptine_utils.h"


int est_nom_fichier_comptine(char * );
int read_until_nl(int fd, char *buf);
struct comptine *init_cpt_depuis_fichier(const char *dir_name, const char *base_name);
void liberer_comptine(struct comptine *cpt);
struct catalogue *creer_catalogue(const char *dir_name);
void liberer_catalogue(struct catalogue *c);
void envoyer_liste(int fd, struct catalogue *c);
uint16_t recevoir_liste_comptines(int fd);
void envoyer_num_comptine(int fd, uint16_t nc);

int main(int argc, char ** argv) {
	uint16_t k = 12;
	printf("k = ?: ");
	envoyer_num_comptine(1, k);
	/**				Teste de est_nom_fichier_comptine : 						********/
	char mot[45];
	
	printf("--------------------------------\n");
	printf("Tester si un fichier à l'extension .cpt ou non : \n");
	printf("-> Entrer le nom de fichier:\t");
	scanf("%s", mot);
	
	if (est_nom_fichier_comptine(mot)) {
		printf("\t<<  Réponse:\tOUI   >>\n\n");
	}
	else {
		printf("\t<<  Réponse:\tNON   >>\n\n");
	}
	
		
		
		
	/**			Teste de read_until_nl :										********/
	char buf[256];
	printf("--------------------------------\n");
	printf("Tester la fonction read_until_nl : (nb_octets) && (Affichage de la ligne) \n");
	printf("->Etrer une ligne: \n");
	printf("\tNombre d'octets:  %d\n", read_until_nl(1, buf));
	printf("\tLigne:  %s\n", buf);
	
	
	
	
	/**		 	Teste init_cpt_depuis_fichier :									*******/		
	char dir_name[50];
	char base_name[50];

	printf("--------------------------------\n");
	printf("Tester init_cpt_depuis_fichier: \n");
	printf("-> Entrer le nom de fichier .cpt: ");
	scanf("%s", base_name);
	
	printf("-> Entrer le nom de dossier: ");
	scanf("%s", dir_name);
	struct comptine * cpt = init_cpt_depuis_fichier(dir_name, base_name);
	printf("\tNom de fichier .cpt : %s\n", cpt->nom_fichier);
	printf("\tTitre de la comptine : %s\n", cpt->titre);
	
	liberer_comptine(cpt);
	
	
	
	/**			Teste creer_catalogue : 			***/
	struct catalogue * c = creer_catalogue("comptines");
	int i;
	printf("--------------------------------\n");
	printf("Affichage de catalogue créer à partir de dossier ./comptines: \n");
	for(i = 0 ; i < c->nb ; i++) {
		printf(" %d. \n", i);
		printf("\tNom de fichier .cpt : %s\n", c->tab[i]->nom_fichier);
		printf("\tTitre de la comptine : %s\n", c->tab[i]->titre);
	}
	
	/**			Tester envoyer_liste(fd, c)		  ***/
	printf("--------------------------------\n");
	printf("Teste de envoyer_liste(fd, c): \n");
	printf("-> !Faut faire cat sur \"file.txt\" pour vérifier, Sorry!\n");
	sleep(5);
	printf("\tTu m'as cru? ne pars pas je vais t'afficher ce qu'ils recoivent dans l'autre côté:\n");
	
	int fd = open("file.txt", O_RDONLY | O_WRONLY | O_TRUNC | O_CREAT);
	envoyer_liste(fd, c);
	uint16_t nb = recevoir_liste_comptines(fd);
	printf("%"PRIu16"\n", nb);
	
	liberer_catalogue(c);
	
	return 0;
}

int est_nom_fichier_comptine(char *nom_fich)
{
	char extension[5];		
	strcpy(extension, nom_fich + (strlen(nom_fich) - 4));
	if (strcmp(extension, ".cpt") == 0) {
		return 1;
	} 
	
	return 0;

}

int read_until_nl(int fd, char *buf)
{
	int nb_octets = 0, n = 0;
	while((n = read(fd, buf + nb_octets, 1)) > 0) {
		if (buf[nb_octets] == '\n') {
			break;
		}
		nb_octets++;
	}
	
	buf[nb_octets + 1] = '\0';
	return nb_octets;
}

struct comptine *init_cpt_depuis_fichier(const char *dir_name, const char *base_name)
{ 
	struct comptine * cpt = malloc(sizeof(struct comptine *));
	cpt->nom_fichier = malloc(sizeof(char) * strlen(base_name) + 1);
	strcpy(cpt->nom_fichier, base_name);
	
	char file_path[100];
	strcpy(file_path, dir_name);
	strcat(file_path, "/");
	strcat(file_path, base_name);
	
	int fd1 = open(file_path, O_RDONLY);
	char t[256];
	int title_size = read_until_nl(fd1, t);
	cpt->titre = malloc(sizeof(char) * (title_size + 1));
	strcpy(cpt->titre, t);
	
	return cpt;
}

void liberer_comptine(struct comptine *cpt)
{
	free(cpt->titre);
	free(cpt->nom_fichier);
	free(cpt);
}

struct catalogue *creer_catalogue(const char *dir_name)
{
	struct dirent * sd;
	DIR * dir;
	if (!(dir = opendir(dir_name))) {
		perror("opendir");
		exit(3);
	}
	
	struct catalogue * c = malloc(sizeof(struct catalogue *));
	int sizeTab = 5;
	c->tab = malloc(sizeof(struct comptine *) * sizeTab);
	
	while((sd = readdir(dir)) != NULL) {
		if (!est_nom_fichier_comptine(sd->d_name)) {
			continue;
		}
		
		c->nb++;
		if (c->nb > sizeTab) {
			sizeTab += 5;
			c->tab = realloc(c->tab, sizeTab * sizeof(struct comptine *));
		}
		
		c->tab[c->nb - 1] = init_cpt_depuis_fichier(dir_name, sd->d_name);
	}
	return c;
}

void liberer_catalogue(struct catalogue *c)
{
	if (c != NULL) {
		for(int i = 0; i < (c->nb) ; i++) {
			liberer_comptine(c->tab[i]);
		}
		free(c->tab);
		free(c);
		c = NULL;
	}	
}


void envoyer_liste(int fd, struct catalogue *c)
{
	/* À définir */
	int i = 0;
	char forme[25];
	for(i = 0 ; i < c->nb ; i++) {
		sprintf(forme, "\t%d ", i);
		write(fd, forme, strlen(forme));
		write(fd, c->tab[i]->titre, strlen(c->tab[i]->titre));
	}	
}


uint16_t recevoir_liste_comptines(int fd)
{	
	int n;
	uint16_t nb_comptines = 0;
	char buffer[257];
	
	while((n = read(fd, buffer, 256)) > 0) {
		sscanf(buffer, "\t%"SCNu16, &nb_comptines);
		write(0, buffer, n);
	}

	
	return nb_comptines;
}


void envoyer_num_comptine(int fd, uint16_t nc)
{
	int s = write(fd, &nc, sizeof(nc));
	if (s == -1) {
		perror("Erreur dans l'écriture dans le fichier");
		exit(4);
	}
}
	
		


