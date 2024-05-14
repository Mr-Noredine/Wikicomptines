/**********************

	 Noureddine		MOHAMMEDI 		12209923
	 
	-> Je déclare qu'il s'agit de mon propre travail.
	-> Ce travail a été réalisé intégralement par un être humain. 
	

**********************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <dirent.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>
#include <dirent.h>
#include "comptine_utils.h"

int read_until_nl(int fd, char *buf)
{
	/* À définir */
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

int est_nom_fichier_comptine(char *nom_fich)
{
	char extension[5];		
	strcpy(extension, nom_fich + (strlen(nom_fich) - 4));
	if (strcmp(extension, ".cpt") == 0) {
		return 1;
	} 
	
	return 0;
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

/* Source : "https:\//www.developpez.net/forums/d1196700/c-cpp/c/debuter/lister-fichiers-repertoire" */

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
