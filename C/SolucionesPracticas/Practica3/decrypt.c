#include <stdio.h>
#include <stdlib.h>

/* Parte 1: algoritmo de descifrado
 * 	v: puntero a un bloque de 64 bits.
 * 	k: puntero a la clave para descifrar.
 * 	Sabiendd que unsiged int equivale a 4 bytes (32 bits)
 * 	Podemos usar la notación de array con v y k
 * 	v[0] v[1] --- k[0] ... k[3]
 */
void decrypt(unsigned int *v, unsigned int *k)
{
	//Definir variables e inicializar los valores de delta y sum
	unsigned int delta = 0x9e3779b9;
	unsigned int sum = 0xC6EF3720;
	int i;
	//Repetir 32  veces (usar un bucle) la siguiente secuencia de operaciones de bajo nivel
	for(i = 0; i < 32; i++){
			//Restar a v[1] el resultado de la operacion :
				// (v[0] desplazado a la izquierda 4 bits +k[2]) XOR (v[0] + sum)  XOR (v[0] desplazado a la derecha 5 bits+k[3])
			v[1] -= ((v[0]<<4) + k[2]) ^(v[0]+sum) ^((v[0]>>5) + k[3]);
			//Restar a v[0] el resultado de la operacion:
				// (v[1] desplazado a la izquierda 4 bits + k[0]) XOR (v[1]+ sum)  XOR (v[1] desplazado a la derecha 5 bits+k[1])
			v[0]-= ((v[1]<<4) + k[0])^(v[1]+sum)^((v[1]>>5) + k[1]);
			// Restar a sum el valor de delta
			sum-=delta;
	}
}

/* Parte 2: Metodo main. Tenemos diferentes opciones para obtener el nombre del fichero cifrado y el descifrado
 * 1. Usar los argumentos de entrada (argv)
 * 2. Pedir que el usuario introduzca los nombres por teclado
 * 3. Definir arrays de caracteres con los nombres
 */
int main(int argc, char *argv[] )
{
	/*Declaración de las variables necesarias, por ejemplo:
	* variables para los descriptores de los ficheros ( FILE * fCif, *fDes)
	* la constante k inicializada con los valores de la clave
	* buffer para almacenar los datos (puntero a unsigned int, más adelante se reserva memoria dinámica */
	FILE *fCif, *fDes;
	unsigned int *buffer;
	unsigned int k[4]={128,129,130,131};

	if(argc<3){
		perror("Faltan argumentos de entrada <fichero cifrado> <fichero descifrado>");
		exit(EXIT_FAILURE);
	}


	/*Abrir fichero encriptado fCif en modo lectura binario
	 * nota: comprobar que se ha abierto correctamente*/
	fCif = fopen(argv[1], "rb");
	if(fCif == NULL){
		perror("No se puede abrir fichero cifrado");
		exit(EXIT_FAILURE);
	}

	/*Abrir/crear fichero fDes en modo escritura binario
	 * nota: comprobar que se ha abierto correctamente*/
	fDes = fopen(argv[2], "wb");
	if(fDes == NULL){
		perror("No se peude abrir fichero descifrado");
		fclose(fCif);
		exit(EXIT_FAILURE);
	}
    

   /*Al comienzo del fichero cifrado esta almacenado el tamaño en bytes que tendrá el fichero descifrado.
    * Leer este valor (imgSize)*/
	int imgSize, offset;
	fread(&imgSize, sizeof(int),1,fCif);
	offset= (imgSize%8 == 0)?0:(8 - (imgSize%8));

	/*Reservar memoria dinámica para el buffer que almacenara el contenido del fichero cifrado
	 * nota1: si el tamaño del fichero descifrado (imgSize) no es múltiplo de 8 bytes,
	 * el fichero cifrado tiene además un bloque de 8 bytes incompleto, por lo que puede que no coincida con imgSize
	 * nota2: al reservar memoria dinámica comprobar que se ha realizó de forma correcta */
	buffer = (unsigned int*)malloc(imgSize+offset);
	if(buffer == NULL){
		perror("Error al reservar memoria dinámica");
		fclose(fDes);
		fclose(fCif);
		exit(EXIT_FAILURE);
	}
	/*Leer la información del fichero cifrado, almacenado el contenido en el buffer*/
	int leidos = fread(buffer,1,(imgSize+offset), fCif);
	//int leidos = fread(buffer,sizeof(char),(imgSize+offset), fCif);
	//int leidos = fread(buffer,(imgSize+offset),1, fCif);
	printf("Leidos %d imgSize %d offset %d\n", leidos, imgSize,offset);

	/*Para cada bloque de 64 bits (8 bytes o dos unsiged int) del buffer, ejectuar el algoritmo de descifrado*/
	int i;
	for(i=0; i <(imgSize+offset)/4; i+=2){
		decrypt(&buffer[i], k);
	}

    /*Guardar el contenido del buffer en el fichero fDes
     * nota: en fDes solo se almacenan tantos bytes como diga imgSize */
	fwrite(buffer,1, imgSize,fDes);
	free(buffer);

	/*Cerrar los ficheros*/
	fclose(fDes);
	fclose(fCif);
	return EXIT_SUCCESS;

}











