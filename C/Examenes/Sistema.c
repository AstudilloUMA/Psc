#include <stdlib.h>
#include <stdio.h>
#include "Sistema.h"
#include <string.h>

//Crea una lista vacia
void Crear(LSistema *l)
{
    *l = NULL;
}

//Inserta un proceso por orden de llegada.
void InsertarProceso ( LSistema *ls, int numproc)
{
    if(*ls == NULL)
    {
        LSistema nuevoNodo = (LSistema)malloc(sizeof(struct Nodo));
        if(nuevoNodo == NULL) perror("InsertarProceso: Error al reservar memoria");
        else 
        {
            nuevoNodo->pid = numproc;
            nuevoNodo->sig = NULL;
            nuevoNodo->sigHebras = NULL;
            *ls = nuevoNodo;
        }
    }
    else
    {   
        LSistema aux = *ls;
        while(aux->sig != NULL)
        {
            aux = aux->sig;
        }

        LSistema nuevoNodo = (LSistema)malloc(sizeof(struct Nodo));
        if(nuevoNodo == NULL) perror("InsertarProceso: Error al creservar memoria");
        else 
        {
            nuevoNodo->pid = numproc;
            nuevoNodo->sig = NULL;
            nuevoNodo->sigHebras = NULL;
            aux->sig = nuevoNodo;
        }    
    }
}

//Inserta una hebra en el proceso numproc teniendo en cuenta el orden de prioridad (mayor a menor)
void InsertarHebra (LSistema *ls, int numproc, char *idhebra, int priohebra)
{
    LSistema auxP = *ls;
    while (auxP != NULL && auxP->pid != numproc)
    {
        auxP = auxP->sig;
    }

    if (auxP->sigHebras == NULL)
    {
        LHebras nuevaHebra = (LHebras)malloc(sizeof(struct NodoHebra));
        if(nuevaHebra == NULL) perror("InsertarHebra: Error al reservar memoria");
        else
        {
            strcpy(nuevaHebra->hebra,idhebra);
            nuevaHebra->prio = priohebra;
            nuevaHebra->sig = NULL;
            auxP->sigHebras = nuevaHebra;
        }
    }
    else
    {
        LHebras antH = NULL;
        LHebras auxH = auxP->sigHebras;

        while(auxH != NULL && priohebra < auxH->prio)
        {
            antH = auxH;
            auxH = auxH->sig;
        }

        LHebras nuevaHebra = (LHebras)malloc(sizeof(struct NodoHebra));
        if(nuevaHebra == NULL) perror("InsertarHebra: Error al reservar memoria");
        else
        {
            strcpy(nuevaHebra->hebra,idhebra);
            nuevaHebra->prio = priohebra;
            nuevaHebra->sig = NULL;
        }

        if(antH == NULL)
        {
            nuevaHebra->sig = auxH;
            auxP->sigHebras = nuevaHebra;
        }
        else
        {
            nuevaHebra->sig = auxH;
            antH->sig = nuevaHebra;
        }
    }   
}

//Muestra el contenido de las hebras
void MostrarHebras (LHebras lh){
    while (lh != NULL)
    {
        printf("\tHebra: %s\t Prioridad: %d\n", lh->hebra, lh->prio);
        lh = lh->sig;
    }  
}

//Muestra el contenido del sistema
void Mostrar (LSistema ls)
{
    while (ls != NULL)
    {
        printf("Proceso: %d\n", ls->pid);
        if(ls->sigHebras != NULL) MostrarHebras(ls->sigHebras);
        printf("------------------------------------\n");
        ls = ls->sig;
    } 
}

void EliminaHebras (LHebras *lh)
{
    LHebras aux = *lh;
    while (*lh != NULL)
    {
        aux = *lh;
        *lh = (*lh)->sig;
        free(aux);
    }  
}

//Elimina del sistema el proceso con número numproc liberando la memoria de éste y de sus hebras.
void EliminarProc (LSistema *ls, int numproc){
    LSistema ant = NULL;
    LSistema aux = *ls;

    while (aux != NULL && aux->pid != numproc)
    {
        ant = aux;
        aux = aux->sig;
    }

    if(aux == NULL)
    {
        *ls = aux->sig;
    }
    else
    {
        ant->sig = aux->sig;
    }
    EliminaHebras(&(aux->sigHebras));
    free(aux);
}

//Destruye toda la estructura liberando su memoria
void Destruir (LSistema *ls)
{
    LSistema aux;
    while (*ls != NULL)
    {
        aux = *ls;
        *ls = (*ls)->sig;
        EliminaHebras(&(aux->sigHebras));
        free(aux);
    }
}



