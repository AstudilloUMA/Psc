/**
UNIX Shell Project

Sistemas Operativos
Grados I. Informatica, Computadores & Software
Dept. Arquitectura de Computadores - UMA

Some code adapted from "Fundamentos de Sistemas Operativos", Silberschatz et al.

To compile and run the program:
   $ gcc Shell_project.c job_control.c -o Shell
   $ ./Shell          
	(then type ^D to exit program)

**/

#include "job_control.h"   // remember to compile with module job_control.c 
#include <string.h>

#define MAX_LINE 256 /* 256 chars per line, per command, should be enough. */

job *jobs;

void manejador(int signal)
{
	block_SIGCHLD();
	job *item;
	int status;
	int info;
	int pid_wait = 0; //Un proceso no puede tener pid 0
	enum status status_res;

	for (int i = 1; i <= list_size(jobs); i++)
	{
		item = get_item_bypos(jobs, i);
		pid_wait = waitpid(item->pgid, &status, WUNTRACED || WNOHANG); //WNOHANG, mira si ha cambiado de estado, si no lo ha hecho se va, si lo ha hecho lo recoge
		
		if(pid_wait == item->pgid) //Si waitpid no recoge a nadie el pid_wait sera 0 y si lo recoge coincidiran, por lo tanto he recogido a alguien
		{
			status_res = analyze_status(status, &info); //Para saber a que estado a cambiado
			if(status_res == SUSPENDED)
			{
				printf("\nBackground job suspended... pid: %d, command: %s\n", item->pgid, item->command);
				item->state = STOPPED;
			}
			else if(status_res == EXITED)
			{
				printf("\nBackground job exited... pid: %d, command: %s\n", item->pgid, item->command);
				delete_job(jobs, item);
			}
		}
	}
	unblock_SIGCHLD();
}

// -----------------------------------------------------------------------
//                            MAIN          
// -----------------------------------------------------------------------

int main(void)
{
	char inputBuffer[MAX_LINE]; /* buffer to hold the command entered */
	int background;             /* equals 1 if a command is followed by '&' */
	char *args[MAX_LINE/2];     /* command line (of 256) has max of 128 arguments */
	// probably useful variables:
	int pid_fork, pid_wait; /* pid for created and waited process */
	int status;             /* status returned by wait */
	enum status status_res; /* status processed by analyze_status() */
	int info;				/* info processed by analyze_status() */

	job *item;
	int primerplano = 0;

	ignore_terminal_signals();
	signal(SIGCHLD, manejador);
	jobs = new_list("Jobs");

	while (1)   /* Program terminates normally inside get_command() after ^D is typed*/
	{   		
		printf("COMMAND->");
		fflush(stdout);
		get_command(inputBuffer, MAX_LINE, args, &background);  /* get next command */
		
		if(args[0]==NULL) continue;   // if empty command

		if(!strcmp(args[0], "cd"))
		{
			int exist;
			exist = chdir(args[1]); //cd solo tiene un argumento
			if(exist == -1) printf("ERROR: the directory doesn't exist\n\n");
			continue;
		}

		if (!strcmp(args[0], "jobs"))
		{
			block_SIGCHLD();
			print_job_list(jobs);
			unblock_SIGCHLD();
			continue;
		}

		if(!strcmp(args[0], "bg"))
		{
			block_SIGCHLD();
			int pos = 1;

			if(args[1] != NULL) //Si args[1] == NULL significa que el usuario no ha introducido un numero
			{
				pos = atoi(args[1]); //Para convertir string a int
			}

			item = get_item_bypos(jobs, pos);
			if(item != NULL && item->state == STOPPED)
			{
				item->state = BACKGROUND;
				killpg(item->pgid, SIGCONT);
			}
			else
			{
				if(pos > list_size(jobs)) printf("ERROR: this position is out of range\n\n");
				else printf("ERROR: this job is not suspended\n\n");
			}
			unblock_SIGCHLD();

			continue;
		} 

		if(!strcmp(args[0], "fg"))
		{
			block_SIGCHLD();
			int pos = 1;

			if(args[1] != NULL) //Si args[1] == NULL significa que el usuario no ha introducido un numero
			{
				pos = atoi(args[1]); //Para convertir string a int
			}

			item = get_item_bypos(jobs, pos);
			if(item != NULL) //No comprobamos que sea en segundo plano porque en nuestra lista solo guardamos procesos que esten en segundo plano o suspendidos
			{
				set_terminal(item->pgid);
				if(item->state == STOPPED)
				{
					killpg(item->pgid, SIGCONT);
				}
				pid_fork = item->pgid;
				delete_job(jobs, item);
			}
			else
			{
				if(item != NULL) printf("ERROR: the position is out of range");
			}
			unblock_SIGCHLD();	

			if(pid_fork > 0) //Zona del padre
			{
				if(background == 0)
				{
					waitpid(pid_fork, &status, WUNTRACED); //El padre espera a que el hijo cambie de estado
					set_terminal(getpid()); //Recuperamos el terminal
					status_res = analyze_status(status, &info); //Analizamos como ha finalizado el hijo
					
					if(status_res == SUSPENDED)
					{
						block_SIGCHLD();
						item = new_job(pid_fork, args[0], STOPPED);
						add_job(jobs, item);
						printf("\nForeground pid: %d, command: %s, status: %s, info: %d\n", pid_fork, args[0], status_strings[status_res], info);
						unblock_SIGCHLD();
					}
					else if(status_res == EXITED)
					{
						if(info != 255) //Siempre que salimos con error info devuelve un 255
						{
							printf("\nForeground pid: %d, command: %s, status: %s, info: %d\n", pid_fork, args[0], status_strings[status_res], info);
						}
					}
				}
				else
				{
					block_SIGCHLD();
					item = new_job(pid_fork, args[0], BACKGROUND);
					add_job(jobs, item);
					printf("\nBackground job running... pid: %d, command: %s\n", pid_fork, args[0]);
					unblock_SIGCHLD();
				}
			}
			else //Zona del hijo
			{
				new_process_group(getpid());
				if(background == 0) set_terminal(getpid());
				restore_terminal_signals();

				execvp(args[0], args); //Comando, argumentos
				printf("\nError, command not found: %s\n", args[0]);
				exit(-1); //Salir con un error
			}
		}
		else
		{
			
		/* the steps are:
			 (1) fork a child process using fork()
			 (2) the child process will invoke execvp()
			 (3) if background == 0, the parent will wait, otherwise continue 
			 (4) Shell shows a status message for processed command 
			 (5) loop returns to get_commnad() function
		*/
			pid_fork = fork(); //Crea una imagen del proceso

			if(pid_fork > 0) //Zona del padre
			{
				if(background == 0)
				{
					waitpid(pid_fork, &status, WUNTRACED); //El padre espera a que el hijo cambie de estado
					set_terminal(getpid()); //Recuperamos el terminal
					status_res = analyze_status(status, &info); //Analizamos como ha finalizado el hijo
					
					if(status_res == SUSPENDED)
					{
						block_SIGCHLD();
						item = new_job(pid_fork, args[0], STOPPED);
						add_job(jobs, item);
						printf("\nForeground pid: %d, command: %s, status: %s, info: %d\n", pid_fork, args[0], status_strings[status_res], info);
						unblock_SIGCHLD();
					}
					else if(status_res == EXITED)
					{
						if(info != 255) //Siempre que salimos con error info devuelve un 255
						{
							printf("\nForeground pid: %d, command: %s, status: %s, info: %d\n", pid_fork, args[0], status_strings[status_res], info);
						}
					}
				}
				else
				{
					block_SIGCHLD();
					item = new_job(pid_fork, args[0], BACKGROUND);
					add_job(jobs, item);
					printf("\nBackground job running... pid: %d, command: %s\n", pid_fork, args[0]);
					unblock_SIGCHLD();
				}
			}
			else //Zona del hijo
			{
				new_process_group(getpid());
				if(background == 0) set_terminal(getpid());
				restore_terminal_signals();

				execvp(args[0], args); //Comando, argumentos
				printf("\nError, command not found: %s\n", args[0]);
				exit(-1); //Salir con un error
			}
		}
		


	} // end while
}
