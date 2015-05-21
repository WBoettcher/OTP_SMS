# OTP_SMS
Android SMS app with one time pad encryption

Feature Tracker

Planned:
Key transfer:
[]  Decrypt the key using the passcode and the QR code, write to persistent memory using Encrypted Key Storage module.
[]  Transfer the key via NFC
[]  MicroSD Card; store as a file on the card and hand-off.  (Consider problems with flash storage recovery and stolen flash storage devices.)
[]  QR Code for small quantities of messages (maybe).

Encrypted Key Storage:
[]  QR lock
[]  Passcode lock
[]  NFC chip lock (potential)
[]  Delete as it gets used; track current position from original key.

SMS Protocols
[]  Interface with network and receive all messages
[]  Send SMS messages using the network

Plaintext Storage
[]  Volatile memory storage option (OR'd) -- Never store plaintext of message in persistent memory
      Snapchat Syndrome(?) - could use security bits to prevent crude attacks?
[]  Stored ones encrypted using passcode/qr lock combo (or just one)
[]  Encrypted using NFC chip lock
      Most convenient solution; just integrate an NFC chip on your daily carry

User Interface
[]  Simple clean interface
[]  Fast startup
[]  Low battery usage

Entropy Collection
[]  Interface with video camera; pull bits from the camera(s).
      Use the front-facing camera?
[]  Interface with the microphone to pull audio entropy
[]  Unify all different sources of entropy
[]  Split into hashable segments, concatenate resulting hashes to produce key
[]  Encrypt the key with a QR code and passcode
[]  Store the encrypted key
[?] Split entropy collection to allow each device to produce 1/2 of the key which is then combined in the end.
      Justification:  Further reduces residual patterns in the entropy collection by introducing interdevice entropy. Also may prevent the full key from existing in plaintext.
  
