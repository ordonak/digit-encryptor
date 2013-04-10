Author: Kenneth Ordona
Date: 10/29/12
Name: PA2
Version: 1.0

Summary: This program is an implementation of the RSA algorithm. More specifically, 
it is a two-part program in two java classes: Makekeys and Encode. The Makekeys class 
can be run by simply mounting the project in Eclipse and then clicking the run button
(signified by a play-button in Eclipse), and then choosing the Makekeys class. The same
concept can be applied to the running of the Encode class. The Makekeys class, when run,
creates a public and private key, called Bob.Public and Bob.Private which are stored in 
the local directory. The Encode class, when run, encrypts or decrypts a chosen input file.

Makekeys class functionality:
	This class has a simple UI control-flow. Once run, the user is prompted with a 
question of how large they want their keys to be. We assume that the user inputs a proper
numeric value; normal behavior of program is not promised if the user inputs the 
value correctly.

Encode class functionality:
	This class also has a simple UI control-flow. Once run, the user is prompted
with a simple question: encrypt or decrypt. There is simple error-checking implemented 
here; if the user inputs a incorrect choice, they are forced to try again. After this, 
the user is prompted once more, this time for the text that is to be ciphered or 
enciphered. If the text is to be encrypted, the encrypted text is output to encrypt.txt. 
If the text is to be decrypted, it will be output to decrypt.txt .

Test-scheme: In order to test these programs, simply run the Makekeys function with the 
desired bit-key size. Then, with the new keys that have been made(Bob.private & Bob.public)
run the Encode class. Choose to encrypt a plaintext file, read the output and then choose 
to decrypt the ciphertext(encrypt.txt). If done correctly, decrypt.txt should be the 
same as the input file. This means that the RSA algorithm is correct.