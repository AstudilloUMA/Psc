#include <stdlib.h>
#include <stdio.h>
#include "Polinomio.h"

/*Crea el polinomio 0 (es decir, un polinomio vac�o).*/
void polinomioCero(TPolinomio *p)
{
    *p = NULL;
}

/*Devuelve el grado del polinomio, es decir, el mayor exponente de los
monomios que no son nulos. En el ejemplo, el grado es 7.
El grado del polinomio cero es 0.*/
unsigned int grado(TPolinomio p)
{
    unsigned int mayor = 0;
    if(p != NULL)
    {
        while (p != NULL)
        {
            if (p->exp > mayor) mayor = p->exp;
            p = p->sig;
        }
    }
    return mayor;  
}

/* Devuelve el coeficiente del exponente exp del polinomio p.*/
unsigned int coeficiente(TPolinomio p, unsigned int exp)
{
    while (p != NULL && p->exp != exp)
    {
        p = p->sig;
    }

    if(p == NULL) return 0;
    else return p->coef;
}

/* Insertar el monomio con coeficiente coef, y exponente exp en el polinomio,
 * de manera que el polinomio quede ordenado. Asegurarse que no se insertan
 * monomios cuyo coeficiente sea 0 y tampoco dos monomios con el mismo exponente.
 * Si al insertar un monomio ya hay otro con el mismo exponente los coeficientes
 * se sumar�n. Se puede asumir que el valor del coeficiente siempre ser� un numero
 * natural (entero no negativo).*/
void insertar(TPolinomio *p, unsigned int coef, unsigned int exp)
{
        TPolinomio anterior = NULL;
        TPolinomio actual = *p;
        TPolinomio nuevoNodo = (TPolinomio)malloc(sizeof(struct TMonomio));
        if(nuevoNodo == NULL) perror("insertar: error al reservar memoria");
        else
        {
            nuevoNodo->coef = coef;
            nuevoNodo->exp = exp;

            if(actual == NULL)
            {
                nuevoNodo->sig = NULL;
                *p = nuevoNodo;
            }
            else
            {
                while (actual->sig != NULL && actual->exp > exp)
                {
                    anterior = actual;
                    actual = actual->sig;
                }

                if(anterior == NULL)
                {
                    if(actual->exp == exp) actual->coef = actual->coef + coef;
                    else
                    {
                        if(actual->exp > exp)
                        {
                            nuevoNodo->sig = NULL;
                            actual->sig = nuevoNodo;
                        } 
                        else
                        {
                           nuevoNodo->sig = actual;
                           *p = nuevoNodo;
                        }
                    }                     
                }
                else
                {
                    if(actual->exp == exp) actual->coef = actual->coef + coef;
                    else
                    {
                        nuevoNodo->sig = actual;
                        anterior->sig = nuevoNodo;
                    }
                }
            }
        }
}

/*Escribe por la pantalla el polinomio con un formato similar al siguiente:
 * [(3,7)(0,6)(2,5)(0,4)(3,3)(0,2)(5,1)(9,0)] para el polinomio ejemplo.
 * Ten en cuenta que los monomios de exponente menor al grado del polinomio
 * con coeficiente 0 tambi�n aparecen en la salida, aunque no est�n almacenados
 * en el polinomio. */
void imprimir(TPolinomio p)
{
    printf("[");
    while (p != NULL)
    {
        printf("(%d,%d)", p->coef, p->exp);
        if(p->sig != NULL)
        {
            unsigned int i = p->exp -1;
            unsigned int d = (p->sig)->exp;
            while (i > d && i > 0)
            {
                printf("(0,%d)",i);
                i--;
            }
        }
        p = p->sig;
    }
    printf("]\n");
    fflush(stdout);
}

/* Elimina todos los monomios del polinomio haciendo que el polinomio resultante
 * sea el polinomio 0.*/
void destruir(TPolinomio *p)
{
    TPolinomio anterior = *p;
    TPolinomio actual = NULL;
    while (anterior->sig != NULL)
    {
        actual = anterior->sig;
        free(anterior);
        anterior = actual;
    }
    free(anterior);
    polinomioCero(p);
}