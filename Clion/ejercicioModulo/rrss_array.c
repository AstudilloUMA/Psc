#include "rrss_array.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>



int readPost(TPost *post){
    printf("Introduzca el timestamp -> ");
    scanf(" %d", &post->ts);


    return 0;
}


void showPost(TPost post){
    //TODO
}


void clearPost(TPost *post){
    //TODO
}


void clearTimeline(TTimeline *tl){
    //TODO
}


void showTimeline(TTimeline tl){
    //TODO
}


void showUserPost(TTimeline tl, char *username){
    //TODO
}

int publishPost(TTimeline *tl, TPost post){
    //TODO
    return 0;
}

int likePost(TTimeline tl, int postId){
    //TODO
    return 0;
}
