/*
 * testLista.c
 *
 *  Created on: 2023
 *      Author: Monica Pinto
 */
#include <stdio.h>
#include <stdlib.h>
#include "lista.h"

/* Muestra por pantalla el contenido del fichero de texto.
   El fichero está formado por un conjunto de líneas, cada
   una con la información de un frame del vídeo.
   
   El formato de cada línea es: <nFrame> <nombreFichero>
   Ejemplo:
     0 frameNumero0
	 6 frameNumero6
*/
void mostrarFichero(char * fichero){
	FILE * f = fopen(fichero,"rt");
	int nFrame;
	char nombre[30];
	while (fscanf(f, " %d %s", &nFrame, nombre))
	{
		printf("%d %s", nFrame, nombre);
	}
	fclose(f);
}

/* Mueve todos los nodos de la lista "eliminados" a la lista "lista"
   Las dos listas (lista y eliminados) habrán sido creadas antes
   de llamar a esta función. Cualquiera de ellas podría estar vacía.

   Al terminar esta función:
     - "lista": contendrá los nodos de ambas listas ordenados
                de forma ascendente por el número de frame. 
	 - "eliminados": será una vacía
   
   NOTA: Para manipular las listas solo podrán utilizarse las funciones 
         disponibles en lista.h
*/
void regenerarVideo(TLista *lista, TLista *eliminados){
	unsigned int nFrame;
	char nombre[30];
	while (eliminar(eliminados,&nFrame,nombre))
	{
		insertar(lista, (*eliminados)->nFrame, (*eliminados)->nombre);
	}
}

int main(void){

	TLista video, eliminados;
	unsigned int numFrames;

	printf("\nCREAMOS LA LISTA ...\n");
	fflush(stdout);
	crear(&video);
	printf("Lista vacia? %s\n", (estaVacia(video)?"TRUE":"FALSE"));
	fflush(stdout);
	printf("Contenido lista: "); mostrar(video);
	fflush(stdout);
	printf("\nINSERTAMOS LOS FRAMES CON NOMBRE frame0003 frame0007 frame0001 frame0003 frame0009 frame0000 frame0006...\n");
	fflush(stdout);
	insertar(&video, 3, "frame0003");
	insertar(&video, 7, "frame0007");
	insertar(&video, 1, "frame0001");
	insertar(&video, 3, "frame0003");
	insertar(&video, 9, "frame0009");
	insertar(&video, 0, "frame0000");
	insertar(&video, 6, "frame0006");
	printf("Contenido lista:\n"); mostrar(video);
	printf("ELIMINAMOS LOS FRAMES CON SALTO 1 ...\n");
	fflush(stdout);
	FILE *felim = fopen("eliminados.txt","wb");
	if (felim == NULL){
		perror("Error creando el fichero");
		exit(-1);
	}
	sacar(&video, 1, &numFrames, felim);
    printf("El numero de frames extraidos es %d\n",numFrames);
	printf("ELIMINAMOS LOS FRAMES CON SALTO 6 ...\n");
	fflush(stdout);
	sacar(&video, 6, &numFrames, felim);
    printf("El numero de frames extraidos es %d\n",numFrames);
	
	printf("ELIMINAMOS LOS FRAMES CON SALTO 2 ...\n");
	fflush(stdout);
	sacar(&video, 2, &numFrames, felim);
	fclose(felim);
    printf("El numero de frames extraidos es %d\n",numFrames);
	if (numFrames > 0){
		printf("Los frames extraidos son:\n");
		mostrarFichero("eliminados.txt");
	}

	printf("CREAMOS UNA LISTA CON LOS FRAMES ELIMINADOS ...\n");
	felim = fopen("eliminados.txt","rb");
	if (felim == NULL){
		perror("Error creando el fichero");
		exit(-1);
	}
	crearDesdeFichero(&eliminados,felim);
	fclose(felim);
	mostrar(eliminados);

	printf("REGENERAMOS EL VIDEO ORIGINAL...\n");
	regenerarVideo(&video,&eliminados);
	mostrar(video);

    printf("Lista vacia? %s\n", (estaVacia(eliminados)?"TRUE":"FALSE"));
	destruir(&video);
	printf("Lista vacia? %s\n", (estaVacia(video)?"TRUE":"FALSE"));
	fflush(stdout);

	return EXIT_SUCCESS;
}