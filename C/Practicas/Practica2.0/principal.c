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
	FILE* fichero = fopen(nombreFich,"wb");

	if (fichero == NULL)
	{
		perror("CrearFichero: El fichero no se ha creado correctamente");
	}
	else
	{
		srand(time(NULL));
		for(int i = 0; i < TAM; i++)
		{
			int num = rand()%TAM;
			fwrite(&num,sizeof(int),1,fichero);
		}
		fclose(fichero);
	}
}

void mostrarFicheroAleatorios(char *nombreFich)
{
	FILE* fichero = fopen(nombreFich,"rb");

	if (fichero == NULL)
	{
		perror("MostrarFicheroAleatorios: El fichero no se ha abierto correctamente");
	}
	else
	{
		int num;
		
		while (fread(&num,sizeof(int),1,fichero))
		{
			printf(" %d ",num);
		}
		
		printf("\n");
		fclose(fichero);
	}
}

void cargaFichero(char* nfichero, T_Arbol* miarbol)
{
	FILE* fichero = fopen(nfichero,"rb");

	if (fichero == NULL)
	{
		perror("CargarFichero: El fichero no se ha abierto correctamente");
	}
	else
	{
		int num;

		while (fread(&num,sizeof(int),1,fichero))
		{
			insertar(miarbol,num);
		}

		fclose(fichero);
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













































