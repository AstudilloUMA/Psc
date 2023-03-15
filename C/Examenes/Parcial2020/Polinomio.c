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

/* Lee los datos de un polinomio de un fichero de texto, y crea la lista de
monomios p. El formato del polinomio en el fichero contiene una secuencia
de pares de dígitos correspondientes al coeficiente y exponente de cada
monomio del polinomio, incluyendo los que tienen coeficiente nulo. En ambos
casos, suponemos que los coeficientes y exponentes son dígitos del 0 al 9
(no hay números superiores). Por ejemplo, para el polinomio de ejemplo el
fichero de texto estaría compuesto por la secuencia de caracteres
“0690332551370402”. Observa que los monomios pueden venir desordenados en
el fichero de entrada. La conversión de un valor de tipo ‘char’ que
contenga un valor númerico (ej. char c = ‘2’) a su correspondiente valor
entero (int valor), se puede hacer de la siguiente forma: valor = c – ‘0’
*/
int crearDeFichero(TPolinomio *p, char *nombre)
{
    FILE* f = fopen(nombre,"rt");
    if(f == NULL) perror("crearDeFichero: error al abrir fichero");
    else
    {
        char coef, exp;
        while (!feof(f))
        {
            fscanf(f,"%c%c", &coef, &exp);
            unsigned int coeficiente = coef - '0';   
            unsigned int exponente = exp -'0';
            insertar(p,coeficiente,exponente);
        }
        fclose(f);
    }
}

/* Eval�a el polinomio para el valor x y devuelve el resultado.
 * Para la evaluaci�n del polinomio debes utilizar el m�todo de Horner,
 * de manera que ax^4 + bx^3+ cx^2+dx+e puede evaluarse
 * en un valor cualquiera x teniendo en cuenta que es equivalente
 * a: (((ax+b)x+c)x+d)x+e.
*/

int hallarPotencia(int x, unsigned int exp)
{
    if(exp > 0) return x*(hallarPotencia(x,exp-1));
    else return 1;
}

int evaluar(TPolinomio p,int x)
{
    int res = 0;
    while (p != NULL)
    {
        res += (p->coef)*hallarPotencia(x,p->exp);
        p = p->sig;
    }
    return res;   
}
