#include <stdlib.h>
#include <stdio.h>
#include "Mprocesos.h"

void Crear(LProc *lroundrobin)
{
    *lroundrobin = NULL;
}

void AnadirProceso(LProc *lroundrobin, int idproc)
{
    LProc nuevoNodo = (LProc)malloc(sizeof(struct T_Nodo));
    if(nuevoNodo == NULL) perror("AnadirProceso: Error al reservar memoria");
    else
    {
        nuevoNodo->pid = idproc;
        nuevoNodo->sig = NULL;

        if (*lroundrobin == NULL)
        {
            *lroundrobin = nuevoNodo;
            nuevoNodo->sig = nuevoNodo;
        }
        else
        {
            nuevoNodo->sig = (*lroundrobin)->sig;
            (*lroundrobin)->sig = nuevoNodo;
            (*lroundrobin) = nuevoNodo;
        }
    }   
}

void EjecutarProcesos(LProc lroundrobin)
{
    if(lroundrobin == NULL) printf("Lista vacia\n");
    else
    {
        LProc actual = lroundrobin->sig;
        do
        {
            printf("\tProceso: %d\n", actual->pid);
            actual = actual->sig;   
        } while (actual != lroundrobin->sig);
    }
}

void EliminarProceso(int id, LProc *lista)
{
    if(*lista == (*lista)->sig)
    {
        free(*lista);
        *lista == NULL;
    }
    else
    {
        LProc ant = NULL;
        LProc actual = *lista;
        do
        {
            ant = actual;
            actual = actual->sig;
        } while (actual->pid != id && actual != *lista);
        
        ant->sig = actual->sig;

        if(actual == *lista) *lista = ant;

        free(actual);
    }
}

int tamanio(LProc lista)
{
    int tam = 0;
    if (lista != NULL)
    {
        LProc actual = lista->sig;
        do
        {
            tam++;
            actual = actual->sig;
        } while (actual != lista);
    }
    return tam;
}

void EscribirFichero (char * nomf, LProc *lista)
{
    FILE* fichero = fopen(nomf,"wb");
    LProc actual = *lista;
    if(fichero == NULL) perror("EscribirFichero: Error al abrir fichero");
    else
    {
        int tam = tamanio(*lista);
        fwrite(&tam,sizeof(int),1,fichero);

        for (int i = 0; i < tam; i++)
        {
            actual = (*lista)->sig;
            fwrite(&(actual->pid),sizeof(int),1,fichero);
            (*lista)->sig = actual->sig;
            free(actual);
        }
        *lista = NULL;
        fclose(fichero);
    }
}
    