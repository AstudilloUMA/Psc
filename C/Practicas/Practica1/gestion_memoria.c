#include <stdio.h>
#include <stdlib.h>
#include "gestion_memoria.h"

const unsigned MAX = 950;

/* Crea la estructura utilizada para gestionar la memoria disponible. Inicialmente, s�lo un nodo desde 0 a MAX */
	void crear(T_Manejador* manejador){
        *manejador = (T_Manejador)malloc(sizeof(struct T_Nodo));
        if(*manejador == NULL){
            perror("Error al crear");
            return;
        }

        (*manejador)->inicio = 0;
        (*manejador)->fin = MAX-1;
        (*manejador)->sig = NULL;
    }

/* Destruye la estructura utilizada (libera todos los nodos de la lista. El par�metro manejador debe terminar apuntando a NULL */
	void destruir(T_Manejador* manejador){
        T_Manejador aux;
        while (*manejador != NULL)
        {
            aux = *manejador;
            *manejador = (*manejador)->sig;
            free(aux);
        }  
    }   

/* Devuelve en �dir� la direcci�n de memoria �simulada� (unsigned) donde comienza el trozo de memoria continua de tama�o �tam� solicitada.
Si la operaci�n se pudo llevar a cabo, es decir, existe un trozo con capacidad suficiente, devolvera TRUE (1) en �ok�; FALSE (0) en otro caso.
 */
	void obtener(T_Manejador *manejador, unsigned tam, unsigned* dir, unsigned* ok){
        T_Manejador* aux = (*manejador)->sig;
        *ok = 0;
        while (*manejador != NULL && ok == 0)
        {
            if(((*aux)->inicio-(*manejador)->fin) >= tam)
            {
                *dir = (*manejador)->fin+1;
                *ok = 1;
            }else
            {
                manejador = aux;
                aux = (*aux)->sig;
            }
        }
    }

/* Muestra el estado actual de la memoria, bloques de memoria libre */
	void mostrar (T_Manejador manejador){
        int numBloque = 0;
        printf("\n**** Mostrando Memoria ****\n");
        while (manejador != NULL)
        {
            printf("Bloque %d\t Inicio: %d\t Fin: %d\n", numBloque, manejador->inicio, manejador->fin);
            manejador = manejador->sig;
            numBloque++;
        }    
    } 

/* Devuelve el trozo de memoria continua de tama�o �tam� y que
 * comienza en �dir�.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
	void devolver(T_Manejador *manejador,unsigned tam,unsigned dir){
        int encontrado = 1;
        while(manejador != NULL && !encontrado){
           if((*manejador)->inicio == dir && ((*manejador)->fin-(*manejador)->inicio) == tam)encontrado = 0; 
           else manejador = (*manejador)->sig;  
        }
    }
