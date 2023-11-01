file_name = "output3.txt"
import random
with open(file_name, "w") as file:
    # 2. Write the text to the file
    for i in range(65536):
        if(i!= 65535):
            file.write(str(random.randint(0,1001))+ '\n')
        else :
            file.write(str(random.randint(0,1001)))