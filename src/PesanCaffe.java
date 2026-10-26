import javax.swing.*; // untuk komponen non layout
import java.awt.*; // untuk layout manager dan warna
import java.awt.event.*; // untuk event handling

// mengimplements 2 interface
public class PesanCaffe implements ActionListener, MouseListener {

    // data menu
    String[] namaMenu  = {"Kopi Hitam", "Cappuccino", "Latte", "Teh Tarik", "Roti Bakar", "Kentang Goreng"};
    int[]    hargaMenu = {10000, 18000, 20000, 12000, 15000, 14000};
    int[]    jumlah    = new int[6]; // jumlah pesanan tiap menu, awalnya 0 semua

    Color coklatTua   = new Color(101, 67, 33);
    Color krem        = new Color(255, 248, 220);
    Color oranyeMuda  = new Color(255, 204, 153);
    Color oranyeTua   = new Color(255, 140, 0);
    Color hijau       = new Color(76, 175, 80);
    Color merah       = new Color(229, 57, 53);

    // komponen
    JFrame frame;
    JPanel panelUtama, panelAtas, panelHeader, panelData, panelMenu, panelPesanan, panelTombol;
    JLabel labelJudul, labelNama, labelMeja, labelDaftar, labelTotal;
    JTextField textNama;
    JComboBox<String> comboMeja;
    JButton[] tombolMenu = new JButton[6];
    JButton tombolKirim, tombolReset;
    JTextArea areaPesanan;
    JScrollPane scroll;

    int total = 0; // menyimpan total belanja

    public PesanCaffe() {
        // top level container
        frame = new JFrame("Sistem Pesan Caffe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null); // frame muncul di tengah layar

        // intermediate containers
        // panel header
        panelHeader = new JPanel();
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelHeader.setBackground(coklatTua);

        labelJudul = new JLabel("UPNORMAL COFFEE");
        labelJudul.setFont(new Font("Arial", Font.BOLD, 26));
        labelJudul.setForeground(Color.WHITE);
        panelHeader.add(labelJudul);

        // panel data pelanggan
        panelData = new JPanel();
        panelData.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelData.setBackground(krem);

        labelNama = new JLabel("Nama:");
        textNama = new JTextField(15);
        labelMeja = new JLabel("Meja:");

        String[] daftarMeja = new String[10];
        for (int i = 0; i < 10; i++) {
            daftarMeja[i] = "Meja " + (i + 1);
        }
        comboMeja = new JComboBox<String>(daftarMeja);

        panelData.add(labelNama);
        panelData.add(textNama);
        panelData.add(labelMeja);
        panelData.add(comboMeja);

        // panel atas (header + data pelanggan)
        panelAtas = new JPanel();
        panelAtas.setLayout(new GridLayout(2, 1));
        panelAtas.add(panelHeader);
        panelAtas.add(panelData);

        GridLayout layoutMenu = new GridLayout(3, 2);
        layoutMenu.setHgap(10);
        layoutMenu.setVgap(10);

        // panel menu
        panelMenu = new JPanel();
        panelMenu.setLayout(layoutMenu);
        panelMenu.setBackground(krem);
        panelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // membuat 6 tombol menu dengan perulangan
        for (int i = 0; i < 6; i++) {
            tombolMenu[i] = new JButton(namaMenu[i] + " - Rp " + hargaMenu[i]);
            tombolMenu[i].setFont(new Font("Arial", Font.BOLD, 14));
            tombolMenu[i].setBackground(oranyeMuda);
            tombolMenu[i].setForeground(coklatTua);
            tombolMenu[i].setOpaque(true);
            tombolMenu[i].setBorderPainted(false);
            panelMenu.add(tombolMenu[i]);
        }

        // panel pesanan
        panelPesanan = new JPanel();
        panelPesanan.setLayout(new BorderLayout(5, 5));
        panelPesanan.setBackground(krem);
        panelPesanan.setPreferredSize(new Dimension(250, 0));
        panelPesanan.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        labelDaftar = new JLabel("Daftar Pesanan:");
        labelDaftar.setFont(new Font("Arial", Font.BOLD, 14));
        labelDaftar.setForeground(coklatTua);

        areaPesanan = new JTextArea();
        areaPesanan.setEditable(false); // pelanggan tidak bisa mengetik di sini
        areaPesanan.setFont(new Font("Monospaced", Font.PLAIN, 13));
        scroll = new JScrollPane(areaPesanan); // supaya ada scrollbar kalau pesanan banyak

        labelTotal = new JLabel("Total: Rp 0");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 18));
        labelTotal.setForeground(coklatTua);

        panelPesanan.add(labelDaftar, BorderLayout.NORTH);
        panelPesanan.add(scroll, BorderLayout.CENTER);
        panelPesanan.add(labelTotal, BorderLayout.SOUTH);

        // panel tombol
        panelTombol = new JPanel();
        panelTombol.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelTombol.setBackground(coklatTua);

        tombolKirim = new JButton("Kirim Pesanan");
        tombolKirim.setBackground(hijau);
        tombolKirim.setForeground(Color.WHITE);
        tombolKirim.setOpaque(true);
        tombolKirim.setBorderPainted(false);

        tombolReset = new JButton("Reset");
        tombolReset.setBackground(merah);
        tombolReset.setForeground(Color.WHITE);
        tombolReset.setOpaque(true);
        tombolReset.setBorderPainted(false);

        panelTombol.add(tombolKirim);
        panelTombol.add(tombolReset);

        // panel utama (menggabungkan semua panel)
        panelUtama = new JPanel();
        panelUtama.setLayout(new BorderLayout());
        panelUtama.add(panelAtas, BorderLayout.NORTH);
        panelUtama.add(panelMenu, BorderLayout.CENTER);
        panelUtama.add(panelPesanan, BorderLayout.EAST);
        panelUtama.add(panelTombol, BorderLayout.SOUTH);
        frame.getContentPane().add(panelUtama);

        // event listener
        for (int i = 0; i < 6; i++) {
            tombolMenu[i].addActionListener(this);
            tombolMenu[i].addMouseListener(this);
        }
        tombolKirim.addActionListener(this);
        tombolReset.addActionListener(this);

        frame.setVisible(true);
    }

    // method main hanya untuk menjalankan constructor
    public static void main(String[] args) {
        new PesanCaffe();
    }

    // method bantu
    public void tampilkanPesanan() {
        String isi = "";
        total = 0;

        for (int i = 0; i < 6; i++) {
            if (jumlah[i] > 0) {
                int subtotal = hargaMenu[i] * jumlah[i];
                isi = isi + namaMenu[i] + "\n  " + jumlah[i] + " x " + hargaMenu[i]
                        + " = Rp " + subtotal + "\n";
                total = total + subtotal;
            }
        }

        areaPesanan.setText(isi);
        labelTotal.setText("Total: Rp " + total);
    }

    public void resetPesanan() {
        for (int i = 0; i < 6; i++) {
            jumlah[i] = 0;
        }
        textNama.setText("");
        comboMeja.setSelectedIndex(0);
        tampilkanPesanan();
    }

    // interface 1
    @Override
    public void actionPerformed(ActionEvent e) {
        // cek apakah yang diklik salah satu tombol menu
        for (int i = 0; i < 6; i++) {
            if (e.getSource() == tombolMenu[i]) {
                jumlah[i] = jumlah[i] + 1;
                tampilkanPesanan();
            }
        }

        if (e.getSource() == tombolKirim) {
            String nama = textNama.getText().trim();

            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Isi nama pelanggan dulu ya!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else if (total == 0) {
                JOptionPane.showMessageDialog(frame, "Belum ada menu yang dipilih!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                String meja = (String) comboMeja.getSelectedItem();
                JOptionPane.showMessageDialog(frame,
                        "Terima kasih, " + nama + "!\nPesanan untuk " + meja
                        + " sedang disiapkan.\nTotal: Rp " + total,
                        "Pesanan Terkirim", JOptionPane.INFORMATION_MESSAGE);
                resetPesanan();
            }

        } else if (e.getSource() == tombolReset) {
            resetPesanan();
        }
    }

    // interface 2
    @Override
    public void mouseEntered(MouseEvent e) {
        JButton tombol = (JButton) e.getSource();
        tombol.setBackground(oranyeTua);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton tombol = (JButton) e.getSource();
        tombol.setBackground(oranyeMuda);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}