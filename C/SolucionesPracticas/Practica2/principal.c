/*
 * principal.c
 *
 *  Created on: 21 mar. 2019
 *      Author: Laura
 */


#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <time.h>
#include "arbolbb.h"

void crearFicheroAleatorios(char *nombreFich, int TAM)
{
	FILE *f = fopen(nombreFich, "wb");
	if(f==NULL){
		perror("Crear Fichero: error al abrir fichero en modo wb");
	}else{
		int num;
		int i;
		srand(time(NULL));
		for(i =0; i < TAM; i++){
			num = rand()%TAM;
			fwrite(&num, sizeof(int), 1,f);
		}
		fclose(f);
	}
}

void mostrarFicheroAleatorios(char *nombreFich)
{
	FILE *f = fopen(nombreFich, "rb");
	if(f==NULL){
		perror("Mostrar Fichero: error al abrir fichero en modo rb");
	}else{
		int num;
		printf("Contenido del fichero:\n");
		while(fread(&num, sizeof(int), 1, f)){
			printf("%d\n", num);
		}
		printf("\n");
		fclose(f);
	}
}

void cargaFichero(char* nfichero, T_Arbol* miarbol)
{
	FILE *f = fopen(nfichero, "rb");
	if(f==NULL){
		perror("Cargar Fichero: error al abrir fichero en modo rb");
	}else{
		int num;	
			
		while(fread(&num, sizeof(int), 1, f)){
			insertar(miarbol, num);
		}
		fclose(f);
	}
}

int main(int argc, char **argv)
{
	int TAM;
	char nombreFich[30];

	setvbuf(stdout, NULL, _IONBF, 0);
	printf("Introduzca nombre fichero:\n");
	scanf("%s", nombreFich);

	printf("Introduzca tamaño:\n");
	scanf("%d", &TAM);

	crearFicheroAleatorios(nombreFich, TAM);
	mostrarFicheroAleatorios(nombreFich);

	printf ("\n Ahora lo cargamos en el arbol\n");
	T_Arbol miarbol;
	crear (&miarbol);
	cargaFichero(nombreFich,&miarbol);
	printf ("\n Y lo mostramos ordenado\n");
	mostrar(miarbol);

	printf("\n Ahora lo guardamos ordenado\n");
	FILE * fich;
	fich = fopen (nombreFich, "wb");
	if(fich==NULL){
		perror("main: error al abrir el fichero en modo wb");
	}else{
		salvar (miarbol, fich);
		fclose (fich);
		printf("\n Y lo mostramos ordenado\n");
		mostrarFicheroAleatorios(nombreFich);
	}
	destruir (&miarbol);

}













































