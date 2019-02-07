Comp = {"0":    "0101010",
        "1":    "0111111",
        "-1":   "0111010",
        "D":    "0001100",
        "A":    "0110000",
        "!D":   "0001101",
        "!A":   "0110001",
        "-D":   "0001111",
        "-A":   "0110011",
        "D+1":  "0011111",
        "A+1":  "0110111",
        "D-1":  "0001110",
        "A-1":  "0110010",
        "D+A":  "0000010",
        "D-A":  "0010011",
        "A-D":  "0000111",
        "D&A":  "0000000",
        "D|A":  "0010101",
        "M":    "1110000",
        "!M":   "1110001",
        "-M":   "1110011",
        "M+1":  "1110111",
        "M-1":  "1110010",
        "D+M":  "1000010",
        "D-M":  "1010011",
        "M-D":  "1000111",
        "D&M":  "1000000",
        "D|M":  "1010101"
        }

Dest = {"null": "000",
        "M":    "001",
        "D":    "010",
        "MD":   "011",
        "A":    "100",
        "AM":   "101",
        "AD":   "110",
        "AMD":  "111"
        }

Jump = {"null": "000",
        "JGT":  "001",
        "JEQ":  "010",
        "JGE":  "011",
        "JLT":  "100",
        "JNE":  "101",
        "JLE":  "110",
        "JMP":  "111"
        }

Labels = {"R0":     "0",
          "R1":     "1",
          "R2":     "2",
          "R3":     "3",
          "R4":     "4",
          "R5":     "5",
          "R6":     "6",
          "R7":     "7",
          "R8":     "8",
          "R9":     "9",
          "R10":    "10",
          "R11":    "11",
          "R12":    "12",
          "R13":    "13",
          "R14":    "14",
          "R15":    "15",
          "SCREEN": "16384",
          "KBD":    "24576",
          "SP":     "0",
          "LCL":    "1",
          "ARG":    "2",
          "THIS":   "3",
          "THAT":   "4"
          }

def ParseAInstruction(instruction):
    parsed = bin(int(instruction[1:]))[2:]
    padding = ""
    while (len(padding) + len(parsed) != 16):
        padding += "0"
    parsed = padding + parsed
    return parsed

def ParseCInstruction(instruction):
    dest = "null"
    comp = "0"
    jump = "null"
    if "=" in instruction:
        string = instruction.split("=")
        dest = string[0]
        comp = string[1]
    if ";" in instruction:
        string = instruction.split(";")
        jump = string[-1]
        comp = string[0]
    parsed = "111" + Comp[comp] + Dest[dest] + Jump[jump]
    return parsed

def StripWhitespace(file):
    file = open(file, "r")
    cleanedText = []
    for line in file:
        cleanedLine = ""
        for char in line:
            if char == "/" or char == "\n":
                break
            elif char != " ":
                cleanedLine += char
        if(len(cleanedLine) != 0):
            cleanedText.append(cleanedLine)
    return cleanedText

def Assemble(fileIn, fileOut):
    cleanedText = StripWhitespace(fileIn)
    fileOut = open(fileOut,"w")
    machineCode = []
 
    PopulateLabels(cleanedText)
    PopulateSymbols(cleanedText)
    for line in cleanedText:
        if "(" in line:
            continue
        elif "@" in line:
            fileOut.write(ParseAInstruction(line)+"\n")      
        else:
            fileOut.write(ParseCInstruction(line)+"\n")

def PopulateLabels(text):
    lineNumber = 0
    for line in text:
        if "(" in line:
            string = line.replace("(","")
            string = string.replace(")","")
            if string not in Labels:
                Labels[string] = lineNumber
        if "(" not in line:
            lineNumber += 1

            
def PopulateSymbols(text):
    lineCleaned = ""
    address = 16
    i = 0
    for line in text:
        if "@" in line:
            string = line[1:]
            if string.isdigit() == False:
                if string in Labels:
                    lineCleaned = "@" + str(Labels[string])
                else:
                    lineCleaned = "@" + str(address)
                    Labels[string] = str(address)
                    address += 1
                    line
                text[i] = lineCleaned
        else:
            text[i] = line
            
        i += 1
