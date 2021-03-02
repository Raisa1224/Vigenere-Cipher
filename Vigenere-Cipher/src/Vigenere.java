public class Vigenere {

    public String transformari(String message){
        String message2 = message.toLowerCase();
        message2=message2.replaceAll("\\s","");
        message2=message2.replaceAll("\\.","");
        message2=message2.replaceAll(",","");
        message2=message2.replaceAll("\\?","");
        message2=message2.replaceAll("!","");
        message2=message2.replaceAll(";","");
        message2=message2.replaceAll("\\(","");
        message2=message2.replaceAll("\\)","");
        message2=message2.replaceAll("-","");
        return message2;
    }

    public String intToText(int[] text){
        String alfabet="abcdefghijklmnopqrstuvwxyz";
        String mesaj="";
        for(int i=0; i< text.length; i++){
            mesaj+=alfabet.charAt(text[i]);
        }
        return mesaj;
    }

    public int[] textToInt(String text){
        int[] mesaj = new int[text.length()];
        for(int i=0; i< mesaj.length; i++){
            mesaj[i]= text.charAt(i)-97;
        }
        return mesaj;
    }

    public int[] codifica(int[] message, int[] key){
        int[] codedMessage = new int[message.length];
        for(int index=0; index< message.length; index++){
            codedMessage[index]=(message[index]+key[(index)%key.length])%26;
        }
        return codedMessage;
    }

    public int[] messageFrequency(int[] message, int start, int lungime){
        int[] freq = new int[26];
        for(int i=0; i< 26; i++){
            freq[i]=0;
        }
        for(int i=start; i< message.length; i+=lungime){
            freq[message[i]]++;
        }
        return freq;
    }

    public float indexOfCoincidence(int[] message, int start, int m){
        int length=(message.length-start)/m;
        int[] freq = messageFrequency(message, start, m);
        float IOC = 0;
        for(int i=0; i<=25; i++){
            float n1 = (float) freq[i];
            float n2 = (float) length;
            float n3 = (float) (freq[i]-1);
            float n4 = (float) length-1;
            float fractie1 = n1/n2;
            float fractie2 = n3/n4;
            IOC += fractie1*fractie2;
        }
        return IOC;
    }

    public boolean conditie(int[] message, int m){
        float media =0;
        for(int index=0; index<m; index++){
            float x = indexOfCoincidence(message, index, m);
            media+=x;
        }
        media/=m;
        //System.out.println("media: " + media);
        if(media < 0.065){
            if( 0.065 - media > 0.003) return false;
        }
        else{
            if(media-0.065 > 0.003) return false;
        }
        return true;
    }

    public int gasesteLungimeaCheii(int[] codedMessage){
        int lungime=0;
        do{
            lungime++;
        }while(!conditie(codedMessage,lungime));
        return lungime;
    }

    public float indexOfMutualCoincidence(int[] message1, int[] message2){
        float MIC=0;
        int[] freq1 = messageFrequency(message1, 0,1);
        int[] freq2 = messageFrequency(message2,0,1);
        for(int j=0; j<=25; j++){
            float n1 = (float) freq1[j];
            float n2 = (float) message1.length;
            float n3 = (float) freq2[j];
            float n4 = (float) message2.length;
            float fractie1 = n1/n2;
            float fractie2 = n3/n4;
            MIC += fractie1*fractie2;
        }
        return MIC;
    }

    public boolean conditie2(int[] message1, int[] message2){
        //MIC(mesage1, message2) aprox egal 0.065
        float MIC = indexOfMutualCoincidence(message1,message2);
        if(MIC > 0.065){
            if(MIC - 0.065 > 0.009) return false;
        }
        else{
            if(0.065 -MIC > 0.009) return false;
        }
        return true;
    }

    public int[] shift(int[] message, int m, int i, int s){
        int[] shift = new int[(message.length-i)/m +1];
        int j=0;
        for(int index = i; index< message.length; index+=m){
            shift[j]= (message[index] + s)%26;
            j++;
        }
        return shift;
    }

    public int[] determinaCheia(int[] codedMessage, int[] normalText, int length){
        int[] key = new int[length];
        int s;
        for(int i=0; i<length; i++){
            s=-1;
            do{
                s=s+1;
            }while(!conditie2(normalText,shift(codedMessage,length,i,s)));
            key[i]=(26-s)%26;
        }
        return key;
    }

    public int[] decodifica(int[] codedMessage, int[] key){
        int[] message = new int[codedMessage.length];
        for(int index=0; index< message.length; index++){
            message[index] = (codedMessage[index] - key[index% key.length]+26)%26;
        }
        return message;
    }

    public void afisareMesajCriptat(String mesajCriptat){
        System.out.println("Mesajul Criptat: " + mesajCriptat.toUpperCase());
    }

    public void afisareMesajCriptat(int[] mesajCriptat){
        String mesajCripatString = intToText(mesajCriptat);
        System.out.println("Mesaj criptat: " + mesajCripatString.toUpperCase());
    }

    public void afisareMesajDecriptat(String mesajDecriptat){
        System.out.println("Mesajul Decriptat: " + mesajDecriptat);
    }

    public void afisareMesajDecriptat(int[] mesajDeriptat){
        String mesajCripatString = intToText(mesajDeriptat);
        System.out.println("Mesaj Decriptat: " + mesajCripatString);
    }
}
