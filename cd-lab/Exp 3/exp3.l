%{
int flag = 0;
%}

%%
[\n] {
	flag==0?printf("Only alphabets present\n"):
	printf("Other characters are also present\n");
	flag = 0;
}

[^a-zA-Z] {flag = 1;}

. {}
%%

int yywrap(void) {}

int main(){
	yylex();
	return 0;
}

