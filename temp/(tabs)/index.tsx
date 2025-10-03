import React, { useState } from 'react';
import { StyleSheet, View, Text, Pressable, Dimensions } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

const EYES = {
  style1: ' o    o ',
  style2: ' >    < ',
  style3: ' -    - ',
};

const MOUTHS = {
  style1: ' ㅅ ',
  style2: '  o  ',
  style3: ' --- ',
};

const screenWidth = Dimensions.get('window').width;
const screenHeight = Dimensions.get('window').height;

export default function HomeScreen() {
  const [face, setFace] = useState({
    eyes: EYES.style1,
    mouth: MOUTHS.style1,
  });

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.faceContainer}>
        <View style={styles.face}>
          <Text style={[styles.facePart, styles.eyes]}>{face.eyes}</Text>
          <Text style={[styles.facePart, styles.mouth]}>{face.mouth}</Text>
        </View>
      </View>

      <View style={styles.selectorContainer}>
        <View style={styles.buttonRow}>
          <Pressable style={styles.button} onPress={() => setFace(f => ({ ...f, eyes: EYES.style1 }))}>
            <Text style={styles.buttonText}>{EYES.style1}</Text>
          </Pressable>
          <Pressable style={styles.button} onPress={() => setFace(f => ({ ...f, eyes: EYES.style2 }))}>
            <Text style={styles.buttonText}>{EYES.style2}</Text>
          </Pressable>
          <Pressable style={styles.button} onPress={() => setFace(f => ({ ...f, eyes: EYES.style3 }))}>
            <Text style={styles.buttonText}>{EYES.style3}</Text>
          </Pressable>
        </View>
        <View style={styles.buttonRow}>
          <Pressable style={styles.button} onPress={() => setFace(f => ({ ...f, mouth: MOUTHS.style1 }))}>
            <Text style={styles.buttonText}>{MOUTHS.style1}</Text>
          </Pressable>
          <Pressable style={styles.button} onPress={() => setFace(f => ({ ...f, mouth: MOUTHS.style2 }))}>
            <Text style={styles.buttonText}>{MOUTHS.style2}</Text>
          </Pressable>
          <Pressable style={styles.button} onPress={() => setFace(f => ({ ...f, mouth: MOUTHS.style3 }))}>
            <Text style={styles.buttonText}>{MOUTHS.style3}</Text>
          </Pressable>
        </View>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F3F4F6',
  },
  faceContainer: {
    flex: 3,
    justifyContent: 'center',
    alignItems: 'center',
  },
  face: {
    width: screenWidth * 0.5,
    height: screenWidth * 0.45,
    borderRadius: screenWidth * 0.15,
    backgroundColor: 'white',
    alignItems: 'center',
    borderWidth: 3,
    borderColor: '#374151',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowOpacity: 0.1,
    shadowRadius: 6,
    elevation: 8,
    position: 'relative',
  },
  facePart: {
    fontSize: 32,
    fontWeight: 'bold',
    color: '#1F2937',
    fontFamily: 'monospace',
    position: 'absolute',
  },
  eyes: {
    top: '40%',
  },
  mouth: {
    top: '60%',
  },
  selectorContainer: {
    flex: 2,
    justifyContent: 'center',
    paddingHorizontal: 10,
  },
  buttonRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 15,
  },
  button: {
    flex: 1,
    marginHorizontal: 5,
    paddingVertical: 20,
    backgroundColor: 'white',
    borderRadius: 12,
    alignItems: 'center',
    justifyContent: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.05,
    shadowRadius: 4,
    elevation: 3,
  },
  buttonText: {
    fontSize: 14,
    fontWeight: '600',
    color: '#4B5563',
    fontFamily: 'monospace',
  },
});