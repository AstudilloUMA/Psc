/*
 * gestion_memoria.c
 *
 *  Created on: Feb 17, 2023
 *      Author: laurapanizo
 */
#include "gestion_memoria.h"

#include <stdio.h>
#include <stdlib.h>

void crear(T_Manejador* manejador){
	*manejador = (T_Manejador)malloc(sizeof(struct T_Nodo));
	if(*manejador == NULL){
		perror("Error al crear");
		return;
	}

	(*manejador)->inicio=0;
	(*manejador)->fin = MAX -1;
	(*manejador)->sig = NULL;

}
void destruir(T_Manejador* manejador){
	T_Manejador aux;
	while(*manejador != NULL){
		aux = *manejador;
		*manejador = (*manejador)->sig;
		free(aux);
	}

}
void mostrar (T_Manejador manejador){
	int i = 0;
	printf("******** Mostrando memoria **********\n");
	while(manejador!=NULL){
		printf("Bloque %d Inicio %d Fin %d\n",i,manejador->inicio, manejador->fin);
		manejador = manejador->sig;
		i++;
	}
}

void obtener(T_Manejador *manejador, unsigned tam, unsigned* dir, unsigned* ok){
	T_Manejador actual = *manejador;
	T_Manejador  anterior = NULL;

	while(actual != NULL && (actual->fin - actual->inicio+1 < tam)){
		anterior = actual;
		actual = actual->sig;
	}
	if(actual == NULL){
		*ok = 0;
	}else{
		*ok= 1;
		*dir = actual->inicio;
		if(actual->fin - actual->inicio + 1 == tam){
			//hay que eliminar el nodo de la lista
			if(anterior==NULL){
				//eliminamos primer nodo de la lista
				*manejador = actual->sig;
				//igual que  *manejador = (*manejador)->sig;
			}else{
				//eliminamos nodo de posición intermedia o final
				anterior->sig = actual->sig;
			}
			free(actual);

		}else{
			//hay que modificar el inicio del nodo
			actual->inicio += tam;
		}
	}
}

void compactar(T_Manejador *manejador){
	T_Manejador actual = *manejador;
	while(actual !=NULL && actual->sig!=NULL){
		if(actual->fin + 1 == actual->sig->inicio){
			actual->fin = actual->sig->fin;
			T_Manejador borrar = actual->sig;
			actual->sig = borrar->sig; //es igual que actual->sig = ac
			free(borrar);
		}else{
			actual = actual->sig;
		}
	}

}

void devolver(T_Manejador *manejador,unsigned tam,unsigned dir){
	T_Manejador actual = *manejador;
	T_Manejador anterior = NULL;
	while(actual != NULL && (actual->inicio < dir)){
		anterior = actual;
		actual = actual->sig;
	}

	T_Manejador nuevoNodo =(T_Manejador)malloc(sizeof(struct T_Nodo));
	if(nuevoNodo== NULL){
		perror("Error al devolver");
	}else{
		nuevoNodo->inicio = dir;
		nuevoNodo->fin = dir +tam -1;
		nuevoNodo->sig = actual;

		if(anterior == NULL){
			//inserto en la primera posición de la lista
			*manejador = nuevoNodo;
		}else{
			//inserto en posición intermedia o final
			anterior->sig = nuevoNodo;
		}
		compactar(manejador);
	}

}








