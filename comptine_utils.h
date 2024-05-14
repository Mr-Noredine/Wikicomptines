#ifndef COMPTINE_UTILS_H
#define COMPTINE_UTILS_H

/** Retourne 1 si nom_fich (chaîne terminée par un '\0') se termine par
 * .cpt et 0 sinon. */
int est_nom_fichier_comptine(char *nom_fich);

/** Lit des octets dans le fichier de descripteur fd et les met dans buf
 * jusqu'au prochain '\n' rencontré, y compris.
 * retourne : le nombre d'octets lus avant le premier '\n' rencontré
 * précondition : buf doit être assez grand pour contenir les octets lus
 * jusqu'au premier caractère '\n' y compris. */
int read_until_nl(int fd, char *buf);

/** Une comptine est la donnée de deux chaînes de caractères terminées par
 * un octet nul :
 * - nom_fichier est le nom de base du fichier correspondant
 * - titre est la première ligne du fichier correspondant, avec son '\n'. */
struct comptine {
	char *titre;
	char *nom_fichier;
};

/** Alloue sur le tas et initialise une struct comptine avec le fichier
 * de format comptine (extension ".cpt") de nom de base base_name situé dans
 * le répertoire dir_name. Le titre et le nom de fichier sont de la comptine
 * retournée sont eux-mêmes alloués sur le tas pour contenir :
 * - la première ligne du fichier, avec son '\n', suivi de '\0'
 * - une copie de la chaine base_name avec son '\0'
 * Retourne : l'adresse de la struct comptine nouvellement créée ou bien
 * NULL en cas d'erreur
 */
struct comptine *init_cpt_depuis_fichier(const char *dir_name,
		const char *base_name);

/** Libère toute la mémoire associée au pointeur de comptine cpt */
void liberer_comptine(struct comptine *cpt);

/** Un catalogue est donné par l'adresse d'un tableau de pointeurs de comptines
 * et le nombre de comptines qu'il contient. */
struct catalogue {
	struct comptine **tab;
	int nb;
};

/** Alloue sur le tas un nouveau catalogue de comptines en lisant les fichiers
 * de format comptine (ceux dont le nom se termine par ".cpt") contenus dans le
 * répertoire de nom dir_name.
 * retourne : un pointeur vers une struct catalogue dont :
 * - nb est le nombre de fichiers comptine dans dir_name
 * - tab est un tableau de nb comptines, avec pour chacunes
 *   + nom_fichier égal au nom de base du fichier comptine correspondant
 *   + titre égal à la première ligne du fichier
 * retourne NULL en cas d'erreur. */
struct catalogue *creer_catalogue(const char *dir_name);

/** Libère toutes les ressources associées à l'adresse c et c lui-même */
void liberer_catalogue(struct catalogue *c);

#endif /* ifndef COMPTINE_UTILS_H */
