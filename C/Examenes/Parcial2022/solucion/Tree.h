#include <stdio.h>

typedef struct Node * Tree;
typedef struct Node {
char* name;
double lat, lon;
Tree left, right;
} Node;

// Inicializa un árbol a vacío.
// 0.25 pts.
void inicializarArbol(Tree* ptrTree);
// Asumiendo que el árbol está ordenado (Binary Search Tree),
// se inserta un nuevo nodo ordenado por nombre con los datos
// pasados como parámetros
// 1.75 pts.
void insertarComisaria(Tree* ptrTree, char* name, double lat, double lon);
// Muestra el árbol en orden, es decir, recorrido infijo.
// 1.0 pt.
void mostrarArbol(Tree t);
// Libera toda la memoria y deja el árbol vacío.
// 1.25 pts.
void destruirArbol(Tree* ptrTree);

/*
Además, el sistema debe permitir localizar la comisaria más cercana dada la latitud y longitud de un
incidente, para ello vamos a usar la distancia de Manhattan:
Para devolver la comisaría más cercana, debes calcular la distancia entre el incidente (lat, lon) y cada
comisaría en el árbol; Si la comisaría más cercana A está en (lat’, lon’), la distancia de manhattan se obtiene
como:
𝑑𝑖𝑠𝑡 = |𝑙𝑎𝑡 − 𝑙𝑎𝑡′| + |𝑙𝑜𝑛 − 𝑙𝑜𝑛′|
Encontrarás la función fabs en el fichero cabecera <math.h> para obtener el valor absoluto de un
double.
*/


// Devuelve el nombre de la comisaría más cercana dada una latitud y longitud.
// Si el árbol está vacío, se devuelve NULL.
// 2.0 pt.
char* localizarComisariaCercana(Tree t, double lat, double lon);

// Carga el fichero de texto que tiene la siguiente estructura, un nombre de comisaría
// nunca va a tener más de 255 caracteres de longitud:
// nombre comisaria 1; latitude1; longitude1;
// nombre comisaria 2; latitude2; longitude2;
// 
// y crea un árbol con un nodo por cada línea en ptrTree.

void cargarComisarias(FILE* filename, Tree* ptrTree);
// Guarda el arbol ordenado (recorrido infijo) en un fichero binario.
// Cada nodo será almacenado en el fichero con la siguiente estructura:
// - Un entero con la longitude del campo name.
// - Los carácteres del campo name.
// - Un double con la latitud.
// - Un double con la longitud.
//
// 2.0 pts.
void guardarBinario(char* filename, Tree tree);