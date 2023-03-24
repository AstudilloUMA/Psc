#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Bloques.h"

/**
* Crea una lista de bloques de 512 bytes cada uno, para una cantidad de memoria
*  en bytes proporcionada como par�metro de entrada.
*  El primer bloque de la lista tendr� como direcci�n 0, el segundo 512, el tercero 1024
*  y as� sucesivamente.
*
*  Se puede asumir que tamMemoria es un valor m�ltiplo de 512.
*
*  Si tamMemoria es 0 entonces se crear� una lista vacia.
*/
void crear(ListaBloques *lb, unsigned int tamMemoria)
{
    if(tamMemoria == 0) *lb = NULL;
    else
    {
        ListaBloques nuevoNodo = (ListaBloques)malloc(sizeof(struct Nodo));
        ListaBloques ptr = *lb;
        unsigned int numBloques = 1;
        while (tamMemoria/512 >= 1)
        {
            free(nuevoNodo);
            while (ptr->sig != NULL)
            {
                ptr = ptr->sig;
            }
            nuevoNodo = (ListaBloques)malloc(sizeof(struct Nodo));
            nuevoNodo->dirInicio = numBloques*512;
            nuevoNodo->sig = NULL;
            insertarBloque(lb,nuevoNodo);
            numBloques++;
            tamMemoria -= 512;
            ptr = ptr->sig;
        }
    
    }
}

/**
* Se obtiene un bloque de la lista de bloques libres.
* El primer par�metro es la lista de bloques libres y el segundo par�metro es el bloque
* que ser� devuelto por la funci�n.
*
* - Si la lista de bloques est� vac�a el segundo par�metro ser� NULL.
* - Si la lista de bloques no est� vac�a se devolver� el primer bloque libre, sac�ndolo de
*   la lista y devolviendo en el segundo par�metro
*
* �CUIDADO� Sacar el bloque de la lista no significa liberar la memoria de ese bloque, sino
* que el nodo deja de formar parte de la lista
*/
void obtenerBloque(ListaBloques *lb, ListaBloques *bloque)
{
    if(*lb == NULL) *bloque = NULL;
    else
    {
        *bloque = *lb;
        *lb = (*lb)->sig;
    }
}

/**
 * Se inserta un bloque en la lista de bloques libres
 * El primer par�metro es la lista de bloques libres y el segundo par�metro es el bloque
 * que debe volver a insertarse en la lista.
 * La inserci�n se realizar� de forma ordenada por la direcci�n de inicio del bloque a devolver.
 *
 * �CUIDADO� El nodo que se quiere devolver a la lista ya existe (bloque es un puntero que
 * apunta a ese nodo). No hay que crear el nodo reservando memoria para �l, solo volver a
 * incorporarlo a la lista.
*/
void insertarBloque(ListaBloques *lb, ListaBloques bloque)
{
    ListaBloques ptr = *lb;
    ListaBloques ant = NULL;

    if (ptr == NULL)
    {
        *lb = bloque;
    }
    else
    {
        while (ptr != NULL && ptr->dirInicio < bloque->dirInicio)
        {
            ant = ptr;
            ptr = ptr->sig;
        }

        if(ant == NULL)
        {
            bloque->sig = ptr;
            *lb = bloque;
        }
        else
        {
            bloque->sig = ptr;
            ant->sig = bloque;
        } 
    }  
}

/**
 * Escribe por pantalla la informaci�n de cada uno de los bloques libres
 * almacenados en la lista.
 *
 * El formato de salida debe ser: (<dir1> <dir2> <dir3> � <dirN>)
 */
void imprimir(ListaBloques lb)
{
    printf("( ");
    while (lb != NULL)
    {
        printf("%u ", lb->dirInicio);
        lb = lb->sig;
    }
    printf(")");   
}

/**
 * Borra todos los nodos de la lista y la deja vac�a
 */one
void borrar(ListaBloques *lb)
{
    ListaBloques ptr;
    while (*lb != NULL)
    {
        ptr = *lb;
        *lb = (*lb)->sig;
        free(ptr);
    }
}