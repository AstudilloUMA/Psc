#include <stdio.h>
#include<stdlib.h>
#include<math.h>
#include<string.h>
#include"Tree.h"


void inicializarArbol(Tree* ptrTree){
    *ptrTree=NULL;
}
void insertarComisaria(Tree* ptrTree, char* name, double lat, double lon){
    Tree newNodo=(Tree )malloc(sizeof(struct Node));
    if (newNodo==NULL){
        perror("No se ha podido reservar memoria");
        exit(-1);
    }
    newNodo->name=(char *)malloc(sizeof(char)*255);
    strcpy(newNodo->name,name);
    //newNodo->name=name;
    newNodo->lat=lat;
    newNodo->lon=lon;
    newNodo->left=NULL;
    newNodo->right=NULL;

    if(*ptrTree==NULL){  // arbol vacio
        *ptrTree=newNodo;
    } else{             // buscar posicion donde insertar el nodo
        Tree ant=*ptrTree, ptr=NULL;
        int lado=strcmp(name,(*ptrTree)->name);
        if (lado<0){ // camino izdo
            ptr=(*ptrTree)->left;
        }
        if (lado>0){     // camino dcho 
            ptr=(*ptrTree)->right;
        }
        while(ptr!=NULL){     // recorrido de busqueda. cuando termine tendremos en "ant" el nodo sobre el que enlazar el nuevo nodo
            ant=ptr;
            lado=strcmp(name,ptr->name);
            if (lado<0){ // camino izdo
                ptr=ptr->left;
            }
            if (lado>0){     // camino dcho 
                ptr=ptr->right;
            }
        }
        if (lado==0){     // si el nodo existe no hacemos cambio y liberamos el creado
            free(newNodo);
        } else if(lado<0){   // lo enganchamos a la izda
                    ant->left=newNodo;
                } else{      // lo enganchamos a la dcha
                    ant->right=newNodo;
                }
    }
}

void mostrarArbol(Tree t){
    Tree izq,dcha;
    if (t!=NULL){
       // izq=t->left;         
        fflush(stdout);
        mostrarArbol(t->left);   // primero mostramos la parte izda.

        printf("Comisaria: %s Latitud: %.2f, Longitud: %.2f\n",t->name,t->lat,t->lon);  // se muestra el nodo actual
        //dcha=t->right;         
        mostrarArbol(t->right);  // y luego mostramos la parte dcha.
       
    }
}
void destruirArbol(Tree* ptrTree){
    Tree izq,dcha,act= *ptrTree;
    *ptrTree=NULL;
    if (act!=NULL){
        izq=act->left;         
        dcha=act->right;
        destruirArbol(&izq);
        free(act);
        destruirArbol(&dcha);
    }
    
}

double manhattan(double lat1, double lon1, double lat2, double lon2){
    return fabs(lat1-lat2)+fabs(lon1-lon2);
}
double menor(double a, double b){
    double resp=a;
    if (a>b) resp=b;
    return resp;
}

double menorManhattan(Tree t,Tree *menor,double lat, double lon){
    *menor=t;                    // por defecto el nodo actual será el menor
    Tree izq,der;
    int distmenor=0,mht=0,mhtIz=0,mhtDer=0;
    mht=manhattan(lat,lon,t->lat,t->lon);    // manhattan del nodo actual
    if (t->left!=NULL) {
        mhtIz=menorManhattan(t->left,&izq,lat,lon);  // manhattan izdo
        if (mhtIz<mht){     
            mht=mhtIz;
            *menor=izq;
        }
    }
    if (t->right!=NULL) {
        mhtDer=menorManhattan(t->right,&der,lat,lon);   // manhattan decho
        if (mhtDer<mht){
            mht=mhtDer;
            *menor=der;
        }
    } 
    return mht;     // devuelve por delante el menor de los 3, y en menor devuelve el nodo menor

}
char* localizarComisariaCercana(Tree t, double lat, double lon){
    char *resp=NULL;
    Tree nodoconmenordist;
    double distancia;
    if (t!=NULL){
        distancia=menorManhattan(t,&nodoconmenordist,lat,lon);
        resp=nodoconmenordist->name;
    }
    return resp;
}

void cargarComisarias(FILE* filename, Tree* ptrTree){
    double lat=0,lon=0;
    int leidos=0;
    
    while (!feof(filename)){
        char comi[255];
        fgets(comi,255,filename);
        //leidos=fscanf(filename,"%255[^;]; %lf; %lf;",comi,&lat,&lon);  // no me funciona bien revisar..
        leidos=sscanf(comi,"%255[^;]; %lf; %lf;",comi,&lat,&lon);
        fflush(stdout);
        if (leidos==3) 
            insertarComisaria(ptrTree,comi,lat,lon);
        }
    fclose(filename);   // me he inventado el main, si en el main que dieron lo cierra hay que quitarlo de aqui

}

void escribirFichBin(FILE * f,Tree tree){
    if (tree!=NULL){
        fflush(stdout);
        escribirFichBin(f,tree->left);   // parte izda.
        int lon=strlen(tree->name)+1;
        fwrite(&lon,sizeof(int),1,f);
        fwrite(&tree->name,sizeof(char),lon,f);
        fwrite(&tree->lat,sizeof(double),1,f);
        fwrite(&tree->lon,sizeof(double),1,f);
        escribirFichBin(f,tree->right);  // parte dcha.
    }
}

void guardarBinario(char* filename, Tree tree){
    Tree izq,dcha;
    FILE * fichBinario=fopen(filename,"wb");   
    if( fichBinario==NULL){
        perror("error en la apertura del fichero");
        exit(-1);
    }
    escribirFichBin(fichBinario,tree);
    fclose(fichBinario);
}
