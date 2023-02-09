#include <stdlib.h>
#include <stdio.h>
#include <errno.h>

static int CAPACIDAD = 10;

struct Fecha{
    int dia;
    int mes;
    int anyo;
};

struct SPost{
    char usuario[20];
    int id;
    struct Fecha fecha;
    int likes;
};

int esValido(int dia, int mes, int anyo){

    if(dia > 31 || dia < 1) return 0;
    if(mes > 12 || mes < 1) return 0;
    if(anyo < 2000) return 0;

    return 1;
    
}

void leerDatos(struct SPost timeline[], int posicion){
    struct SPost post;

    printf("\nIntroduzca el nombre de usuario: ");
    scanf(" %s", &post.usuario);

    printf("\nIntroduzca el id del post: ");
    scanf(" %d", &post.id);

    do{
        printf("\nIntroduzca una fecha con el siguiente formato <dd/mm/aaaa>: ");
        scanf(" %d/%d/%d", &post.fecha.dia, &post.fecha.mes, &post.fecha.anyo);
    } while(esValido(post.fecha.dia,post.fecha.mes,post.fecha.anyo) == 0);
    
    printf("\nIntroduzca el numero de likes: ");
    scanf(" %d", &post.likes);

    timeline[posicion] = post;
}

void mostrarTimeline(struct SPost timeline[], int numPost){

    int index = 0;
    
    while(index < numPost){
        struct SPost post = timeline[index];
        printf("\n%d.- Usuario: %s    Post: %d    Fecha: %d/%d/%d    Likes: %d\n", index+1, post.usuario, post.id, post.fecha.dia, post.fecha.mes, post.fecha.anyo, post.likes);
        index++;
    }
}

int main(int argc, char *argv[]){

    int exit = 0;
    struct SPost timeline[CAPACIDAD];

    char comando;
    int numPost = 0;

    printf("\nIntroducir post (p) o mostrar timeline (t): ");
    scanf(" %c", &comando);

    while(comando != 't'){
        if(numPost == CAPACIDAD) {
            perror("\nHas alcanzado la camacidad maxima del timeline");
            return EXIT_FAILURE;
        }

        leerDatos(timeline,numPost);
        numPost++;

        printf("\nIntroducir post (p) o mostrar timeline (t): ");
        scanf(" %c", &comando);   
    }

    mostrarTimeline(timeline,numPost);

    return EXIT_SUCCESS;
}
