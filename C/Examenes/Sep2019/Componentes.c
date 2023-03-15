#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Componentes.h"

/*
La rutina Lista_Vacia devuelve 1 si la lista que se le pasa
como parametro esta vacia y 0 si no lo esta.
*/
int Lista_Vacia(Lista lista)
{
    if(lista == NULL) return 1;
    else return 0;
}

/*Num_Elementos es una funcion a la que se le pasa un puntero a una lista 
y devuelve el numero de elementos de dicha lista.
*/
int Num_Elementos(Lista  lista)
{
    int tam = 0;
    while (lista != NULL)
    {
        tam++;
        lista = lista->sig;
    }
    return tam; 
}

/*
La rutina Adquirir_Componente se encarga de recibir los datos de un nuevo 
componente (codigo y texto) que se le introducen por teclado y devolverlos 
por los parametros pasados por referencia "codigo" y "texto".
*/
 void Adquirir_Componente(long *codigo,char *texto)
 {
    printf("\nIntroduzca el codigo del componente: ");
    scanf(" %ld", codigo);

    printf("\nIntroduzca el texto del fabricante: ");
    scanf(" %s", texto);

    printf("\n----------------------------------------------------------------------\n");

    fflush(stdout);
 }

 /*
La funcion Lista_Imprimir se encarga de imprimir por pantalla la lista 
enlazada completa que se le pasa como parametro.
*/
void Lista_Imprimir(Lista lista)
{
    while (lista != NULL)
    {
        printf("\nId: %ld\t Texto del Fabricante: %s\n", lista->codigoComponente, lista->textoFabricante);
        lista = lista->sig;
    }

    printf("\n----------------------------------------------------------------------\n");

    fflush(stdout);  
}

/*
La funcion Lista_Salvar se encarga de guardar en el fichero binario 
"examen.dat" la lista enlazada completa que se le pasa como parametro. 
Para cada nodo de la lista, debe almacenarse en el fichero
el código y el texto de la componente correspondiente.
*/
void Lista_Salvar(Lista  lista)
{
    FILE * f = fopen("examen.dat","wb");
    if(f == NULL) perror("Lista_Salvar: Error al abrir el fichero");
    else
    {
        while (lista != NULL)
        {
            fwrite(&(lista->codigoComponente),sizeof(long),1,f);
            fwrite(&(lista->textoFabricante),sizeof(char[MAX_CADENA]),1,f);
            lista = lista->sig;
        }
        fclose(f);
        fflush(stdout);   
    }
}

/*
La funcion Lista_Crear crea una lista enlazada vacia
de nodos de tipo Componente.
*/
Lista Lista_Crear()
{
    return NULL;
}

/*
La funcion Lista_Agregar toma como parametro un puntero a una lista,
el código y el texto de un componente y  anyade un nodo al final de 
la lista con estos datos.
*/
void Lista_Agregar(Lista *lista, long codigo, char* textoFabricante)
{   
    Componente* nodoAct = (Componente*)malloc(sizeof(struct elemLista));
    if(nodoAct == NULL) perror("Lista_Agregar: Error al reservar memoria");
    else
    {
        nodoAct->codigoComponente = codigo;
        strcpy(nodoAct->textoFabricante,textoFabricante);
        nodoAct->sig = NULL;

        if ((*lista) == NULL)
        {
            (*lista) = nodoAct;
        }
        else
        {
            nodoAct = (*lista);
            while (nodoAct->sig != NULL)
            {
                nodoAct = nodoAct->sig;
            }

            Componente* nuevoNodo = (Componente*)malloc(sizeof(struct elemLista)); 
            nuevoNodo->codigoComponente = codigo;
            strcpy(nuevoNodo->textoFabricante,textoFabricante);
            nuevoNodo->sig = NULL;

            nodoAct->sig = nuevoNodo;
        }   
    }  
}

/*
Lista_Extraer toma como parametro un puntero a una Lista y elimina el
Componente que se encuentra en su ultima posicion.
*/
void Lista_Extraer(Lista *lista)
{
    Lista actual = *lista;
    Lista ant = NULL;

    if (actual == NULL)
    {
        *lista = NULL;
    }
    else
    {
        if (actual->sig == NULL) 
        {
            free(actual);
            *lista = NULL;
        }
        else
        {
            while (actual->sig != NULL)
            {
                ant = actual;
                actual = actual->sig;
            }

            ant->sig = NULL;
            free(actual);
        }  
    }
    
    
}

/*
Lista_Vaciar es una funcion que toma como parametro un puntero a una Lista
y elimina todos sus Componentes.
*/
void Lista_Vaciar(Lista *lista)
{
    while((*lista) != NULL)
    {
        Lista_Extraer(lista);
    }
    *lista = NULL;
}

