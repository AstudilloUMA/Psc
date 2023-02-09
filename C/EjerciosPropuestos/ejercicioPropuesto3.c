/*Implementar un calculadora simple que realice las operaciones de suma (+), resta (-), multiplicación (*) y división (/) de dos números enteros y muestre por pantalla el resultado. 
    El programa debe dar la opción de hacer un cálculo (el usuario introduce 'c') o terminar (usuario introduce el carácter 'q').  
    El usuario introducirá por teclado la expresión en notación infija, es decir <operando 1> <Operador> <operando 2> 
    (si quieres puedes pedir al usuario que vaya metiendo elemento a elemento y leer con 3 scanf diferentes).*/

#include <stdio.h>
#include <stdlib.h>

int calcular (int n1, int n2, char operador){
    switch(operador){
            case '+': 
                return n1 + n2;
                break;
            case '-':
               return n1 - n2;
                break;
            case '*':
                return n1 * n2;
                break;
            case '/':
                return n1 / n2;
                break;
        }
}

int main(int argc, char ârgv[]){
    int num1, num2, sol;
    char operador;
    char comando;
    int continuar = 1;

    printf("\nQuiere hacer una operacion (c) o terminar el programa (p): ");
    scanf(" %c", &comando);

    while(comando != 'p'){

        printf("\nIntroduzca una operacion (suma, resta, multiplicacion, o division) de la forma <operando1><Operador><operando2>: ");
        scanf(" %d %c %d", &num1, &operador, &num2);

        printf("La solucion a su operacion es: %d\n", calcular(num1,num2,operador));

        printf("\nQuiere hacer una operacion (c) o terminar el programa (p): ");
        fflush(stdout);
        scanf(" %c", &comando);
    }

    return EXIT_SUCCESS;
}