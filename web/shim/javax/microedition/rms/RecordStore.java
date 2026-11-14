package javax.microedition.rms;
import java.util.*;
public class RecordStore {
  public static final int AUTHMODE_PRIVATE = 0, AUTHMODE_ANY = 1;
  static final Map<String, RecordStore> stores = new HashMap<String, RecordStore>();
  final String name; final TreeMap<Integer, byte[]> recs = new TreeMap<Integer, byte[]>();
  int next = 1, version = 0; long modified = System.currentTimeMillis(); boolean open;
  private RecordStore(String n) { name = n; }
  public static synchronized void deleteRecordStore(String n) throws RecordStoreException, RecordStoreNotFoundException {
    if (stores.remove(n) == null) throw new RecordStoreNotFoundException(n);
  }
  public static synchronized String[] listRecordStores() { return stores.keySet().toArray(new String[0]); }
  public static synchronized RecordStore openRecordStore(String n, boolean create) throws RecordStoreException, RecordStoreFullException, RecordStoreNotFoundException {
    RecordStore s = stores.get(n);
    if (s == null) { if (!create) throw new RecordStoreNotFoundException(n); s = new RecordStore(n); stores.put(n, s); }
    s.open = true; return s;
  }
  public static RecordStore openRecordStore(String n, boolean c, int a, boolean w) throws RecordStoreException, RecordStoreFullException, RecordStoreNotFoundException { return openRecordStore(n, c); }
  public static RecordStore openRecordStore(String n, String v, String s) throws RecordStoreException, RecordStoreNotFoundException { return openRecordStore(n, false); }
  public void closeRecordStore() throws RecordStoreNotOpenException, RecordStoreException { open = false; }
  private void chk() throws RecordStoreNotOpenException { if (!open) throw new RecordStoreNotOpenException(); }
  public String getName() throws RecordStoreNotOpenException { chk(); return name; }
  public int getVersion() throws RecordStoreNotOpenException { chk(); return version; }
  public synchronized int getNumRecords() throws RecordStoreNotOpenException { chk(); return recs.size(); }
  public synchronized int getSize() throws RecordStoreNotOpenException { chk(); int s = 0; for (byte[] b : recs.values()) s += b.length; return s; }
  public int getSizeAvailable() throws RecordStoreNotOpenException { chk(); return 1 << 20; }
  public long getLastModified() throws RecordStoreNotOpenException { chk(); return modified; }
  public void addRecordListener(RecordListener l) {}
  public void removeRecordListener(RecordListener l) {}
  public synchronized int getNextRecordID() throws RecordStoreNotOpenException, RecordStoreException { chk(); return next; }
  public synchronized int addRecord(byte[] d, int o, int n) throws RecordStoreNotOpenException, RecordStoreException, RecordStoreFullException {
    chk(); byte[] c = new byte[n]; if (d != null) System.arraycopy(d, o, c, 0, n); recs.put(next, c); version++; modified = System.currentTimeMillis(); return next++;
  }
  public synchronized void deleteRecord(int id) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
    chk(); if (recs.remove(id) == null) throw new InvalidRecordIDException(); version++;
  }
  public synchronized int getRecordSize(int id) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
    chk(); byte[] b = recs.get(id); if (b == null) throw new InvalidRecordIDException(); return b.length;
  }
  public synchronized int getRecord(int id, byte[] buf, int off) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
    byte[] b = getRecord(id); System.arraycopy(b, 0, buf, off, b.length); return b.length;
  }
  public synchronized byte[] getRecord(int id) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
    chk(); byte[] b = recs.get(id); if (b == null) throw new InvalidRecordIDException(); return b.clone();
  }
  public void setMode(int a, boolean w) throws RecordStoreException {}
  public synchronized void setRecord(int id, byte[] d, int o, int n) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException, RecordStoreFullException {
    chk(); if (!recs.containsKey(id)) throw new InvalidRecordIDException();
    byte[] c = new byte[n]; if (d != null) System.arraycopy(d, o, c, 0, n); recs.put(id, c); version++; modified = System.currentTimeMillis();
  }
  public synchronized RecordEnumeration enumerateRecords(RecordFilter f, RecordComparator cmp, boolean keep) throws RecordStoreNotOpenException {
    chk(); final List<Integer> ids = new ArrayList<Integer>();
    for (Map.Entry<Integer, byte[]> e : recs.entrySet()) if (f == null || f.matches(e.getValue())) ids.add(e.getKey());
    if (cmp != null) Collections.sort(ids, new Comparator<Integer>() { public int compare(Integer a, Integer b) { return cmp.compare(recs.get(a), recs.get(b)); } });
    final RecordStore self = this;
    return new RecordEnumeration() {
      int i = 0; boolean ku = keep;
      public int numRecords() { return ids.size(); }
      public byte[] nextRecord() throws InvalidRecordIDException, RecordStoreNotOpenException, RecordStoreException { return self.getRecord(nextRecordId()); }
      public int nextRecordId() throws InvalidRecordIDException { if (i >= ids.size()) throw new InvalidRecordIDException(); return ids.get(i++); }
      public byte[] previousRecord() throws InvalidRecordIDException, RecordStoreNotOpenException, RecordStoreException { return self.getRecord(previousRecordId()); }
      public int previousRecordId() throws InvalidRecordIDException { if (i <= 0) throw new InvalidRecordIDException(); return ids.get(--i); }
      public boolean hasNextElement() { return i < ids.size(); }
      public boolean hasPreviousElement() { return i > 0; }
      public void reset() { i = 0; }
      public void rebuild() {}
      public void keepUpdated(boolean k) { ku = k; }
      public boolean isKeptUpdated() { return ku; }
      public void destroy() {}
    };
  }
}
