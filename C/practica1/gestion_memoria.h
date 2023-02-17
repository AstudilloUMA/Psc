/*
 * gestion_memoria.h
 *
 *  Created on: dd/mm/aaaa
 *  Author: name
 */

#ifndef _GESTION_MEMORIA_
#define _GESTION_MEMORIA_
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

void crear(T_Manejador* manejador){
   
    *manejador = (T_Manejador) malloc (sizeof(struct T_Nodo));
   
    if(*manejador != NULL){
        (*manejador)->inicio = 0;
        (*manejador)->fin = 999;
        (*manejador)->sig = NULL;
    }    
}

void destruir(T_Manejador* manejador){
    T_Manejador aux;
    while(*manejador != NULL){
        aux = *manejador;
        *manejador = (*manejador)->sig;
        free(aux);
    }
}

void obtener(T_Manejador *manejador, unsigned tam, unsigned* dir, unsigned* ok){
    T_Manejador curr = *manejador;
    T_Manejador ant = NULL;
    *ok = 0;
    while(curr != NULL && curr->fin - curr->inicio + 1 < tam){
        ant = curr;
        curr = ant->sig;
    }
    if(curr != NULL){
        *ok = 1;
        *dir = curr->inicio;
        if(curr->fin - curr->inicio + 1 > tam){
            curr->inicio += tam;
        } else{
            if(ant == NULL){
                *manejador = (*manejador)->sig;
                free(curr);
            } else{
                ant->sig = curr->sig;
                free(curr);
            }
        }
    } else{printf("\nNo hay espacio disponible");}
}

void mostrar (T_Manejador manejador){
    T_Manejador aux = manejador;
    while (aux != NULL){
        printf("\nInicio: %d\n Fin: %d\n LIBRE\n--------\n", aux->inicio, aux->fin);
        aux = aux->sig;
    }
}

void compactar(T_Manejador manejador){
    T_Manejador curr = manejador;
    T_Manejador post;
    while(curr != NULL){
        post = curr-> sig;
        while(curr->fin == post->inicio){
            curr->fin = post->fin;
            post = post->sig; 
        }
        curr->sig = post;
    }
}

void devolver(T_Manejador *manejador,unsigned tam,unsigned dir){
    //crear nuevo nodo
    T_Manejador curr = *manejador;
    T_Manejador ant = NULL;

    T_Manejador nNodo;
    nNodo = (T_Manejador) malloc (sizeof(struct T_Nodo));
    nNodo->inicio = dir;
    nNodo->fin = tam-1;
    //insertarlo en manejador en la posición que corresponda
    while(curr != NULL && curr->fin > dir){
        ant = curr;
        curr = curr->sig;
        if(ant != NULL){
            nNodo->sig = curr;
            ant->sig = nNodo; 
        } else{
            ant = nNodo;
        }
    }
    compactar(*manejador);
}

