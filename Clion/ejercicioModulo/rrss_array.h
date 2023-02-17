#ifndef RRSS_H
#define RRSS_H

#define MAX_TL 25

typedef struct SPost {
    int ts; //timestamp: instante de tiempo en el que se publico (ts>0 y ts1 < ts2 --> ts1 se ha publicado antes que ts2)
    int likes; //número de likes
    char username[25]; //nombre de usuario
    int postId; //id numérico del post   
} TPost;

typedef struct STimeline{
    int size; //numero de post, como maximo MAX_TL
    TPost posts[MAX_TL];
}TTimeline;

// Lee de teclado los datos de un post (todos los campos del struct). Devuelve 1 si la lectura ha sido correcta y 0 en otro caso
int readPost(TPost *post);

//Muestra por salida estandar la información de un post (usuario, marca de tiempo y número de likes)
void showPost(TPost post);

//Limpia el post
void clearPost(TPost *post);

//Limpia el timeline. 
void clearTimeline(TTimeline *tl);

//Muestra por pantalla todos los post de un timeline
void showTimeline(TTimeline tl);

//Muestra por pantalla todos los post del un timeline publicados por un usuario dado
void showUserPost(TTimeline tl, char *username);


//Inserta(copia) la publicacion en el timeline - version facil al final, version complicada en orden usando el ts
//Si no hay espacio no modifica el timeline y devuelve 0 y en otro caso devuelve 1 y la inserta
int publishPost(TTimeline *tl, TPost post);

//Incrementa el numero de likes de la publicacion con el id dado
//Si la publicacion no existe en el timeline devuelve 0, en otro caso devuelve 1 e incrementa el contador
int likePost(TTimeline tl, int postId);

#endif