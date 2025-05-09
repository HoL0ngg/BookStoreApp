-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 03, 2025 lúc 06:44 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlythuvien`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chucnang`
--

CREATE TABLE `chucnang` (
  `MaChucNang` int(11) NOT NULL,
  `TenChucNang` varchar(50) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chucnang`
--

INSERT INTO `chucnang` (`MaChucNang`, `TenChucNang`, `Status`) VALUES
(1, 'Xem', 1),
(2, 'Them', 1),
(3, 'Sua', 1),
(4, 'Xoa', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctphieumuon`
--

CREATE TABLE `ctphieumuon` (
  `MaPhieuMuon` int(11) NOT NULL,
  `MaSach` varchar(10) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ctphieumuon`
--

INSERT INTO `ctphieumuon` (`MaPhieuMuon`, `MaSach`, `Status`) VALUES
(1, 'S001', 1),
(1, 'S002', 1),
(1, 'S003', 1),
(2, 'S004', 1),
(2, 'S005', 1),
(2, 'S006', 1),
(2, 'S007', 1),
(2, 'S008', 1),
(3, 'S009', 1),
(3, 'S010', 1),
(4, 'S011', 1),
(4, 'S012', 1),
(4, 'S013', 1),
(4, 'S014', 1),
(5, 'S015', 1),
(6, 'S016', 1),
(6, 'S017', 1),
(6, 'S018', 1),
(6, 'S019', 1),
(6, 'S020', 1),
(7, 'S021', 1),
(7, 'S022', 1),
(7, 'S023', 1),
(8, 'S024', 1),
(8, 'S025', 1),
(9, 'S026', 1),
(9, 'S027', 1),
(9, 'S028', 1),
(9, 'S029', 1),
(10, 'S030', 1),
(10, 'S031', 1),
(10, 'S032', 1),
(10, 'S033', 1),
(10, 'S034', 1),
(11, 'S035', 1),
(11, 'S036', 1),
(12, 'S037', 1),
(12, 'S038', 1),
(12, 'S039', 1),
(13, 'S040', 1),
(13, 'S041', 1),
(13, 'S042', 1),
(13, 'S043', 1),
(14, 'S044', 1),
(15, 'S045', 1),
(15, 'S046', 1),
(15, 'S047', 1),
(15, 'S048', 1),
(15, 'S049', 1),
(16, 'S050', 1),
(16, 'S051', 1),
(17, 'S052', 1),
(17, 'S053', 1),
(17, 'S054', 1),
(18, 'S055', 1),
(18, 'S056', 1),
(18, 'S057', 1),
(18, 'S058', 1),
(19, 'S059', 1),
(20, 'S060', 1),
(20, 'S061', 1),
(20, 'S062', 1),
(20, 'S063', 1),
(20, 'S064', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctphieutra`
--

CREATE TABLE `ctphieutra` (
  `MaCTPhieuTra` int(11) NOT NULL,
  `TrangThai` int(11) DEFAULT NULL,
  `MaPhieuTra` int(11) DEFAULT NULL,
  `MaSach` varchar(255) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 1
) ;

--
-- Đang đổ dữ liệu cho bảng `ctphieutra`
--

INSERT INTO `ctphieutra` (`MaCTPhieuTra`, `TrangThai`, `MaPhieuTra`, `MaSach`, `Status`) VALUES
(1, 0, 1, 'S001', 1),
(2, 1, 2, 'S002', 1),
(3, 2, 3, 'S003', 1),
(4, 0, 4, 'S004', 1),
(5, 1, 5, 'S005', 1),
(6, 2, 6, 'S006', 1),
(7, 0, 7, 'S007', 1),
(8, 1, 8, 'S008', 1),
(9, 2, 9, 'S009', 1),
(10, 0, 10, 'S010', 1),
(11, 1, 11, 'S011', 1),
(12, 2, 12, 'S012', 1),
(13, 0, 13, 'S013', 1),
(14, 1, 14, 'S014', 1),
(15, 2, 15, 'S015', 1),
(16, 0, 16, 'S016', 1),
(17, 1, 17, 'S017', 1),
(18, 2, 18, 'S018', 1),
(19, 0, 19, 'S019', 1),
(20, 1, 20, 'S020', 1),
(21, 2, 21, 'S021', 1),
(22, 0, 22, 'S022', 1),
(23, 1, 23, 'S023', 1),
(24, 2, 24, 'S024', 1),
(25, 0, 25, 'S025', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cttheloai`
--

CREATE TABLE `cttheloai` (
  `MaTheLoai` int(11) NOT NULL,
  `TenTheLoai` varchar(50) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `cttheloai`
--

INSERT INTO `cttheloai` (`MaTheLoai`, `TenTheLoai`, `Status`) VALUES
(1, 'Tiểu thuyết', 1),
(2, 'Tâm lý - Kỹ năng sống', 1),
(3, 'Kinh doanh', 1),
(4, 'Khoa học', 1),
(5, 'Văn học cổ điển', 1),
(6, 'Trinh thám', 1),
(7, 'Self-help', 1),
(8, 'Văn học thiếu nhi', 1),
(9, 'Lịch sử', 1),
(10, 'Triết học', 1),
(11, 'Kỹ năng lãnh đạo', 1),
(12, 'Phát triển bản thân', 1),
(13, 'Truyện ngắn', 1),
(14, 'Tiểu thuyết lịch sử', 1),
(15, 'Công nghệ thông tin', 1),
(16, 'Marketing', 1),
(17, 'Tài chính cá nhân', 1),
(18, 'Du ký', 1),
(19, 'Hồi ký', 1),
(20, 'Văn học nước ngoài', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dausach`
--

CREATE TABLE `dausach` (
  `MaDauSach` varchar(10) NOT NULL,
  `TenDauSach` varchar(100) DEFAULT NULL,
  `HinhAnh` varchar(50) DEFAULT NULL,
  `NhaXuatBan` varchar(100) DEFAULT NULL,
  `NamXuatBan` varchar(4) DEFAULT NULL,
  `NgonNgu` varchar(30) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 1,
  `sotrang` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `dausach`
--

INSERT INTO `dausach` (`MaDauSach`, `TenDauSach`, `HinhAnh`, `NhaXuatBan`, `NamXuatBan`, `NgonNgu`, `SoLuong`, `Status`, `sotrang`) VALUES
('DS001', 'Nhà Giả Kim', 'img1.jpg', 'Nhà xuất bản Trẻ', '1988', 'Tiếng Việt', 5, 1, 10),
('DS002', 'Đắc Nhân Tâm', 'img2.jpg', 'First News', '1936', 'Tiếng Việt', 7, 1, NULL),
('DS003', 'Harry Potter và Hòn đá Phù thủy', 'img3.jpg', 'Bloomsbury', '1997', 'Tiếng Anh', 9, 1, NULL),
('DS004', 'Tiếng Chim Hót Trong Bụi Mận Gai', 'img4.jpg', 'Nhà xuất bản Văn học', '1977', 'Tiếng Việt', 4, 1, NULL),
('DS005', 'Bố Già', 'img5.jpg', 'Nhà xuất bản Văn hóa', '1969', 'Tiếng Việt', 6, 1, NULL),
('DS006', 'Tuổi Trẻ Đáng Giá Bao Nhiêu', 'img6.jpg', 'Nhà xuất bản Hội Nhà Văn', '2016', 'Tiếng Việt', 8, 1, NULL),
('DS007', 'Tôi Tài Giỏi Bạn Cũng Thế', 'img7.jpg', 'Nhà xuất bản Trẻ', '2009', 'Tiếng Việt', 3, 1, NULL),
('DS008', 'Đọc Vị Bất Kỳ Ai', 'img8.jpg', 'Nhà xuất bản Lao động', '2007', 'Tiếng Việt', 5, 1, NULL),
('DS009', 'Cà Phê Cùng Tony', 'img9.jpg', 'Nhà xuất bản Trẻ', '2013', 'Tiếng Việt', 7, 1, NULL),
('DS010', 'Sherlock Holmes Toàn Tập', 'img10.jpg', 'Nhà xuất bản Văn học', '1892', 'Tiếng Việt', 9, 1, NULL),
('DS011', 'Chiến Binh Cầu Vồng', 'img11.jpg', 'Nhà xuất bản Hội Nhà Văn', '2005', 'Tiếng Việt', 4, 1, NULL),
('DS012', 'Lược Sử Thời Gian', 'img12.jpg', 'Bantam Books', '1988', 'Tiếng Anh', 6, 1, NULL),
('DS013', 'Những Người Khốn Khổ', 'img13.jpg', 'Nhà xuất bản Văn học', '1862', 'Tiếng Việt', 8, 1, NULL),
('DS014', 'Trên Đường Băng', 'img14.jpg', 'Nhà xuất bản Trẻ', '2015', 'Tiếng Việt', 2, 1, NULL),
('DS015', 'Đại Nhạc Hội', 'img15.jpg', 'Nhà xuất bản Văn hóa', '2018', 'Tiếng Việt', 5, 1, NULL),
('DS016', 'Sapiens: Lược Sử Loài Người', 'img16.jpg', 'Nhà xuất bản Thế giới', '2011', 'Tiếng Việt', 7, 1, NULL),
('DS017', 'Không Gia Đình', 'img17.jpg', 'Nhà xuất bản Văn học', '1878', 'Tiếng Việt', 9, 1, NULL),
('DS018', 'Nhật Ký Trong Tù', 'img18.jpg', 'Nhà xuất bản Văn học', '1942', 'Tiếng Việt', 3, 1, NULL),
('DS019', 'Tôi Là Bêtô', 'img19.jpg', 'Nhà xuất bản Trẻ', '2007', 'Tiếng Việt', 6, 1, NULL),
('DS020', 'Hoàng Tử Bé', 'img20.jpg', 'Nhà xuất bản Kim Đồng', '1943', 'Tiếng Việt', 8, 1, NULL),
('DS021', 'Dế Mèn Phiêu Lưu Ký', 'img21.jpg', 'Nhà xuất bản Kim Đồng', '1941', 'Tiếng Việt', 4, 1, NULL),
('DS022', 'Quẳng Gánh Lo Đi Và Vui Sống', 'img22.jpg', 'Nhà xuất bản Trẻ', '1948', 'Tiếng Việt', 7, 1, NULL),
('DS023', 'Đi Tìm Lẽ Sống', 'img23.jpg', 'Nhà xuất bản Tổng hợp', '1946', 'Tiếng Việt', 5, 1, NULL),
('DS024', 'Bắt Trẻ Đồng Xanh', 'img24.jpg', 'Nhà xuất bản Văn học', '1951', 'Tiếng Việt', 9, 1, NULL),
('DS025', 'Ông Già Và Biển Cả', 'img25.jpg', 'Nhà xuất bản Văn học', '1952', 'Tiếng Việt', 3, 1, NULL),
('DS026', 'Chạng Vạng', 'img26.jpg', 'Nhà xuất bản Trẻ', '2005', 'Tiếng Việt', 6, 1, NULL),
('DS027', 'Nhà Lãnh Đạo Không Chức Danh', 'img27.jpg', 'Nhà xuất bản Lao động', '2010', 'Tiếng Việt', 8, 1, NULL),
('DS028', 'Đời Thay Đổi Khi Chúng Ta Thay Đổi', 'img28.jpg', 'Nhà xuất bản Trẻ', '2003', 'Tiếng Việt', 4, 1, NULL),
('DS029', 'Tâm Lý Học Đám Đông', 'img29.jpg', 'Nhà xuất bản Thế giới', '1895', 'Tiếng Việt', 7, 1, NULL),
('DS030', 'Bí Mật Của May Mắn', 'img30.jpg', 'Nhà xuất bản Trẻ', '2009', 'Tiếng Việt', 5, 1, NULL),
('DS031', 'Người Bán Hàng Vĩ Đại Nhất Thế Giới', 'img31.jpg', 'Nhà xuất bản Tổng hợp', '1968', 'Tiếng Việt', 9, 1, NULL),
('DS032', 'Tư Duy Nhanh Và Chậm', 'img32.jpg', 'Nhà xuất bản Thế giới', '2011', 'Tiếng Việt', 3, 1, NULL),
('DS033', 'Những Kẻ Xuất Chúng', 'img33.jpg', 'Nhà xuất bản Thế giới', '2008', 'Tiếng Việt', 6, 1, NULL),
('DS034', 'Đàn Ông Sao Hỏa Đàn Bà Sao Kim', 'img34.jpg', 'Nhà xuất bản Trẻ', '1992', 'Tiếng Việt', 8, 1, NULL),
('DS035', '7 Thói Quen Của Người Thành Đạt', 'img35.jpg', 'Nhà xuất bản Trẻ', '1989', 'Tiếng Việt', 4, 1, NULL),
('DS036', 'Người Giàu Có Nhất Thành Babylon', 'img36.jpg', 'Nhà xuất bản Lao động', '1926', 'Tiếng Việt', 7, 1, NULL),
('DS037', 'Nghệ Thuật Tinh Tế Của Việc Đếch Quan Tâm', 'img37.jpg', 'Nhà xuất bản Thế giới', '2016', 'Tiếng Việt', 5, 1, NULL),
('DS038', 'Nhà Giả Kim (Bản Tiếng Anh)', 'img38.jpg', 'HarperCollins', '1988', 'Tiếng Anh', 9, 1, NULL),
('DS039', 'Hạt Giống Tâm Hồn', 'img39.jpg', 'Nhà xuất bản Tổng hợp', '2001', 'Tiếng Việt', 3, 1, NULL),
('DS040', 'Đừng Bao Giờ Đi Ăn Một Mình', 'img40.jpg', 'Nhà xuất bản Lao động', '2005', 'Tiếng Việt', 6, 1, NULL),
('DS041', 'Tốc Độ Của Niềm Tin', 'img41.jpg', 'Nhà xuất bản Trẻ', '2006', 'Tiếng Việt', 8, 1, NULL),
('DS042', 'Cha Giàu Cha Nghèo', 'img42.jpg', 'Nhà xuất bản Trẻ', '1997', 'Tiếng Việt', 4, 1, NULL),
('DS043', 'Nếu Tôi Biết Được Khi Còn 20', 'img43.jpg', 'Nhà xuất bản Trẻ', '2009', 'Tiếng Việt', 7, 1, NULL),
('DS044', 'Đọc Sách Và Con Đường Gian Nan Vạn Dặm', 'img44.jpg', 'Nhà xuất bản Thế giới', '2015', 'Tiếng Việt', 5, 1, NULL),
('DS045', 'Những Bài Học Cuộc Sống', 'img45.jpg', 'Nhà xuất bản Tổng hợp', '2003', 'Tiếng Việt', 9, 1, NULL),
('DS046', 'Từ Tốt Đến Vĩ Đại', 'img46.jpg', 'Nhà xuất bản Trẻ', '2001', 'Tiếng Việt', 3, 1, NULL),
('DS047', 'Nghĩ Giàu Làm Giàu', 'img47.jpg', 'Nhà xuất bản Lao động', '1937', 'Tiếng Việt', 6, 1, NULL),
('DS048', 'Bí Mật Tư Duy Triệu Phú', 'img48.jpg', 'Nhà xuất bản Trẻ', '2005', 'Tiếng Việt', 8, 1, NULL),
('DS049', 'Quốc Gia Khởi Nghiệp', 'img49.jpg', 'Nhà xuất bản Thế giới', '2009', 'Tiếng Việt', 4, 1, NULL),
('DS050', 'Làm Như Chơi', 'img50.jpg', 'Nhà xuất bản Trẻ', '2017', 'Tiếng Việt', 7, 1, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `docgia`
--

CREATE TABLE `docgia` (
  `MaDocGia` varchar(10) NOT NULL,
  `TenDocGia` varchar(100) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SDT` varchar(15) DEFAULT NULL,
  `TrangThai` int(11) DEFAULT NULL CHECK (`TrangThai` in (0,1,2)),
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `docgia`
--

INSERT INTO `docgia` (`MaDocGia`, `TenDocGia`, `DiaChi`, `SDT`, `TrangThai`, `Status`) VALUES
('DG001', 'Nguyễn Văn A', 'Hà Nội', '0987654321', 1, 1),
('DG002', 'Trần Thị B', 'Hồ Chí Minh', '0978123456', 0, 1),
('DG003', 'Lê Văn C', 'Đà Nẵng', '0961234567', 2, 1),
('DG004', 'Phạm Thị D', 'Hải Phòng', '0956781234', 1, 1),
('DG005', 'Hoàng Minh E', 'Cần Thơ', '0945678123', 0, 1),
('DG006', 'Đỗ Ngọc F', 'Bắc Ninh', '0934567812', 2, 1),
('DG007', 'Nguyễn Hữu G', 'Nghệ An', '0923456781', 1, 1),
('DG008', 'Vũ Thanh H', 'Huế', '0912345678', 0, 1),
('DG009', 'Bùi Văn I', 'Thanh Hóa', '0901234567', 2, 1),
('DG010', 'Phan Thị J', 'Nam Định', '0898765432', 1, 1),
('DG011', 'Tô Minh K', 'Quảng Ninh', '0887654321', 0, 1),
('DG012', 'Lương Thị L', 'Bình Dương', '0876543210', 2, 1),
('DG013', 'Ngô Phương M', 'Đồng Nai', '0865432109', 1, 1),
('DG014', 'Dương Tuấn N', 'Vĩnh Phúc', '0854321098', 0, 1),
('DG015', 'Đinh Ngọc O', 'Thái Bình', '0843210987', 2, 1),
('DG016', 'Hà Thanh P', 'Quảng Bình', '0832109876', 1, 1),
('DG017', 'Trương Văn Q', 'Ninh Bình', '0821098765', 0, 1),
('DG018', 'Lý Hồng R', 'Hòa Bình', '0810987654', 2, 1),
('DG019', 'Tống Thị S', 'Hà Tĩnh', '0809876543', 1, 1),
('DG020', 'Đoàn Minh T', 'Phú Thọ', '0798765432', 0, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MaNCC` int(11) NOT NULL,
  `TenNhaCC` varchar(100) NOT NULL,
  `DiaChi` varchar(200) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `SDT` varchar(15) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`MaNCC`, `TenNhaCC`, `DiaChi`, `Email`, `SDT`, `Status`) VALUES
(1, 'Công ty Sách ABC', '123 Đường Lê Lợi, Q.1, TP.HCM', 'contact@abooks.com', '0901234567', 1),
(2, 'Nhà sách Phương Nam', '456 Đường Nguyễn Huệ, Q.1, TP.HCM', 'info@pnsbooks.com', '0907654321', 1),
(3, 'Fahasa', '789 Đường Trần Hưng Đạo, Q.5, TP.HCM', 'support@fahasa.com', '0901122334', 1),
(4, 'Vinabook', '321 Đường Cách Mạng Tháng 8, Q.3, TP.HCM', 'service@vinabook.com', '0904433221', 1),
(5, 'Nhà xuất bản Trẻ', '135 Đường Lý Chính Thắng, Q.3, TP.HCM', 'nxbtre@nxbtre.com', '0905566778', 1),
(6, 'First News', '246 Đường Võ Văn Tần, Q.3, TP.HCM', 'firstnews@firstnews.com', '0906677889', 1),
(7, 'Nhà xuất bản Văn học', '357 Đường Đồng Khởi, Q.1, TP.HCM', 'nxbvanhoc@vanhoc.com', '0907788990', 1),
(8, 'Nhà xuất bản Kim Đồng', '468 Đường Lê Duẩn, Q.1, TP.HCM', 'nxbkimdong@kimdong.com', '0908899001', 1),
(9, 'Nhà xuất bản Thế giới', '579 Đường Nam Kỳ Khởi Nghĩa, Q.3, TP.HCM', 'nxbthegioi@thegioi.com', '0909900112', 1),
(10, 'Nhà xuất bản Tổng hợp', '680 Đường Pasteur, Q.3, TP.HCM', 'nxbtonghop@tonghop.com', '0900011223', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `MaNhanVien` varchar(10) NOT NULL,
  `TenNhanVien` varchar(100) DEFAULT NULL,
  `GioiTinh` tinyint(1) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `SDT` varchar(15) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`MaNhanVien`, `TenNhanVien`, `GioiTinh`, `NgaySinh`, `SDT`, `Status`) VALUES
('NV001', 'Đặng Hoàng Nam', 1, '1991-04-22', '0987112233', 1),
('NV002', 'Lý Thị Mai', 0, '1994-09-18', '0978223344', 1),
('NV003', 'Vũ Tiến Đạt', 1, '1987-11-30', '0968334455', 1),
('NV004', 'Phan Ngọc Bích', 0, '1993-06-12', '0958445566', 1),
('NV005', 'Tạ Hữu Phước', 1, '1986-02-27', '0948556677', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhomquyen`
--

CREATE TABLE `nhomquyen` (
  `MaNhomQuyen` int(11) NOT NULL,
  `TenNhomQuyen` varchar(20) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhomquyen`
--

INSERT INTO `nhomquyen` (`MaNhomQuyen`, `TenNhomQuyen`, `Status`) VALUES
(1, 'QuanLy', 1),
(2, 'NhanVien', 1),
(3, 'DocGia', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhomquyen_chucnang`
--

CREATE TABLE `nhomquyen_chucnang` (
  `MaNhomQuyen` int(11) NOT NULL,
  `MaChucNang` int(11) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhomquyen_chucnang`
--

INSERT INTO `nhomquyen_chucnang` (`MaNhomQuyen`, `MaChucNang`, `Status`) VALUES
(1, 1, 1),
(1, 2, 1),
(1, 3, 1),
(1, 4, 1),
(2, 1, 1),
(2, 2, 1),
(2, 3, 1),
(3, 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieumuon`
--

CREATE TABLE `phieumuon` (
  `MaPhieuMuon` int(11) NOT NULL,
  `NgayMuon` date NOT NULL,
  `NgayTraDuKien` date NOT NULL,
  `TrangThai` tinyint(4) NOT NULL,
  `MaDocGia` varchar(10) NOT NULL,
  `MaNhanVien` varchar(10) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieumuon`
--

INSERT INTO `phieumuon` (`MaPhieuMuon`, `NgayMuon`, `NgayTraDuKien`, `TrangThai`, `MaDocGia`, `MaNhanVien`, `Status`) VALUES
(1, '2023-01-01', '2023-01-15', 1, 'DG001', 'NV001', 1),
(2, '2023-01-02', '2023-01-16', 0, 'DG002', 'NV002', 1),
(3, '2023-01-03', '2023-01-17', 1, 'DG003', 'NV003', 1),
(4, '2023-01-04', '2023-01-18', 0, 'DG004', 'NV004', 1),
(5, '2023-01-05', '2023-01-19', 1, 'DG005', 'NV005', 1),
(6, '2023-01-06', '2023-01-20', 0, 'DG006', 'NV001', 1),
(7, '2023-01-07', '2023-01-21', 1, 'DG007', 'NV002', 1),
(8, '2023-01-08', '2023-01-22', 0, 'DG008', 'NV003', 1),
(9, '2023-01-09', '2023-01-23', 1, 'DG009', 'NV004', 1),
(10, '2023-01-10', '2023-01-24', 0, 'DG010', 'NV005', 1),
(11, '2023-01-11', '2023-01-25', 1, 'DG011', 'NV001', 1),
(12, '2023-01-12', '2023-01-26', 0, 'DG012', 'NV002', 1),
(13, '2023-01-13', '2023-01-27', 1, 'DG013', 'NV003', 1),
(14, '2023-01-14', '2023-01-28', 0, 'DG014', 'NV004', 1),
(15, '2023-01-15', '2023-01-29', 1, 'DG015', 'NV005', 1),
(16, '2023-01-16', '2023-01-30', 0, 'DG016', 'NV001', 1),
(17, '2023-01-17', '2023-01-31', 1, 'DG017', 'NV002', 1),
(18, '2023-01-18', '2023-02-01', 0, 'DG018', 'NV003', 1),
(19, '2023-01-19', '2023-02-02', 1, 'DG019', 'NV004', 1),
(20, '2023-01-20', '2023-02-03', 0, 'DG020', 'NV005', 1),
(21, '2023-01-01', '2023-01-15', 1, 'DG001', 'NV001', 1),
(22, '2023-01-02', '2023-01-16', 0, 'DG002', 'NV002', 1),
(23, '2023-01-03', '2023-01-17', 1, 'DG003', 'NV003', 1),
(24, '2023-01-04', '2023-01-18', 0, 'DG004', 'NV004', 1),
(25, '2023-01-05', '2023-01-19', 1, 'DG005', 'NV005', 1),
(26, '2023-01-06', '2023-01-20', 0, 'DG006', 'NV001', 1),
(27, '2023-01-07', '2023-01-21', 1, 'DG007', 'NV002', 1),
(28, '2023-01-08', '2023-01-22', 0, 'DG008', 'NV003', 1),
(29, '2023-01-09', '2023-01-23', 1, 'DG009', 'NV004', 1),
(30, '2023-01-10', '2023-01-24', 0, 'DG010', 'NV005', 1),
(31, '2023-01-11', '2023-01-25', 1, 'DG011', 'NV001', 1),
(32, '2023-01-12', '2023-01-26', 0, 'DG012', 'NV002', 1),
(33, '2023-01-13', '2023-01-27', 1, 'DG013', 'NV003', 1),
(34, '2023-01-14', '2023-01-28', 0, 'DG014', 'NV004', 1),
(35, '2023-01-15', '2023-01-29', 1, 'DG015', 'NV005', 1),
(36, '2023-01-16', '2023-01-30', 0, 'DG016', 'NV001', 1),
(37, '2023-01-17', '2023-01-31', 1, 'DG017', 'NV002', 1),
(38, '2023-01-18', '2023-02-01', 0, 'DG018', 'NV003', 1),
(39, '2023-01-19', '2023-02-02', 1, 'DG019', 'NV004', 1),
(40, '2023-01-20', '2023-02-03', 0, 'DG020', 'NV005', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieunhap`
--

CREATE TABLE `phieunhap` (
  `MaPhieuNhap` varchar(10) NOT NULL,
  `ThoiGian` date NOT NULL,
  `MaNhanVien` varchar(10) NOT NULL,
  `MaNCC` int(11) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ;

--
-- Đang đổ dữ liệu cho bảng `phieunhap`
--

INSERT INTO `phieunhap` (`MaPhieuNhap`, `ThoiGian`, `MaNhanVien`, `MaNCC`, `Status`) VALUES
('PN001', '2023-01-15', 'NV001', 1, 1),
('PN002', '2023-01-20', 'NV002', 2, 1),
('PN003', '2023-02-05', 'NV003', 3, 1),
('PN004', '2023-02-10', 'NV001', 4, 1),
('PN005', '2023-03-01', 'NV004', 5, 1),
('PN006', '2023-03-15', 'NV005', 6, 1),
('PN007', '2023-04-02', 'NV002', 7, 1),
('PN008', '2023-04-18', 'NV003', 8, 1),
('PN009', '2023-05-05', 'NV001', 9, 1),
('PN010', '2023-05-20', 'NV004', 10, 1),
('PN011', '2023-06-10', 'NV005', 1, 1),
('PN012', '2023-06-25', 'NV002', 2, 1),
('PN013', '2023-07-05', 'NV003', 3, 1),
('PN014', '2023-07-20', 'NV004', 4, 1),
('PN015', '2023-08-01', 'NV005', 5, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieuphat`
--

CREATE TABLE `phieuphat` (
  `MaPhieuPhat` int(11) NOT NULL,
  `TienPhat` int(11) DEFAULT NULL,
  `NgayPhat` date DEFAULT NULL,
  `TrangThai` int(11) DEFAULT NULL CHECK (`TrangThai` in (0,1,2)),
  `MaDocGia` varchar(10) DEFAULT NULL,
  `MaCTPhieuTra` int(11) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieuphat`
--

INSERT INTO `phieuphat` (`MaPhieuPhat`, `TienPhat`, `NgayPhat`, `TrangThai`, `MaDocGia`, `MaCTPhieuTra`, `Status`) VALUES
(1, 0, '2024-01-10', 0, 'DG001', 1, 1),
(2, 75000, '2024-02-15', 1, 'DG002', 2, 1),
(3, 0, '2024-03-01', 0, 'DG003', 3, 1),
(4, 95000, '2024-01-20', 1, 'DG004', 4, 1),
(5, 0, '2024-02-10', 0, 'DG005', 5, 1),
(6, 65000, '2024-03-15', 1, 'DG006', 6, 1),
(7, 0, '2024-01-05', 0, 'DG007', 7, 1),
(8, 80000, '2024-02-18', 1, 'DG008', 8, 1),
(9, 0, '2024-03-22', 0, 'DG009', 9, 1),
(10, 62000, '2024-01-30', 1, 'DG010', 10, 1),
(11, 0, '2024-02-08', 0, 'DG011', 11, 1),
(12, 78000, '2024-03-10', 1, 'DG012', 12, 1),
(13, 0, '2024-01-15', 0, 'DG013', 13, 1),
(14, 97000, '2024-02-25', 1, 'DG014', 14, 1),
(15, 0, '2024-03-05', 0, 'DG015', 15, 1),
(16, 53000, '2024-01-28', 1, 'DG016', 16, 1),
(17, 0, '2024-02-20', 0, 'DG017', 17, 1),
(18, 70000, '2024-03-18', 1, 'DG018', 18, 1),
(19, 0, '2024-01-12', 0, 'DG019', 19, 1),
(20, 68000, '2024-02-27', 1, 'DG020', 20, 1),
(21, 0, '2024-03-08', 0, 'DG001', 21, 1),
(22, 75000, '2024-01-17', 1, 'DG002', 22, 1),
(23, 0, '2024-02-12', 0, 'DG003', 23, 1),
(24, 82000, '2024-03-25', 1, 'DG004', 24, 1),
(25, 0, '2024-01-21', 0, 'DG005', 25, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieutra`
--

CREATE TABLE `phieutra` (
  `MaPhieuTra` int(11) NOT NULL,
  `NgayTra` date DEFAULT NULL,
  `MaNhanVien` varchar(10) DEFAULT NULL,
  `MaDocGia` varchar(10) DEFAULT NULL,
  `MaPhieuMuon` int(11) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieutra`
--

INSERT INTO `phieutra` (`MaPhieuTra`, `NgayTra`, `MaNhanVien`, `MaDocGia`, `MaPhieuMuon`, `Status`) VALUES
(1, '2025-03-01', 'NV001', 'DG001', 1, 1),
(2, '2025-03-02', 'NV002', 'DG002', 2, 1),
(3, '2025-03-03', 'NV003', 'DG003', 3, 1),
(4, '2025-03-04', 'NV004', 'DG004', 4, 1),
(5, '2025-03-05', 'NV005', 'DG005', 5, 1),
(6, '2025-03-06', 'NV001', 'DG006', 6, 1),
(7, '2025-03-07', 'NV002', 'DG007', 7, 1),
(8, '2025-03-08', 'NV003', 'DG008', 8, 1),
(9, '2025-03-09', 'NV004', 'DG009', 9, 1),
(10, '2025-03-10', 'NV005', 'DG010', 10, 1),
(11, '2025-03-11', 'NV001', 'DG011', 11, 1),
(12, '2025-03-12', 'NV002', 'DG012', 12, 1),
(13, '2025-03-13', 'NV003', 'DG013', 13, 1),
(14, '2025-03-14', 'NV004', 'DG014', 14, 1),
(15, '2025-03-15', 'NV005', 'DG015', 15, 1),
(16, '2025-03-16', 'NV001', 'DG016', 16, 1),
(17, '2025-03-17', 'NV002', 'DG017', 17, 1),
(18, '2025-03-18', 'NV003', 'DG018', 18, 1),
(19, '2025-03-19', 'NV004', 'DG019', 19, 1),
(20, '2025-03-20', 'NV005', 'DG020', 20, 1),
(21, '2025-03-21', 'NV001', 'DG001', 21, 1),
(22, '2025-03-22', 'NV002', 'DG002', 22, 1),
(23, '2025-03-23', 'NV003', 'DG003', 23, 1),
(24, '2025-03-24', 'NV004', 'DG004', 24, 1),
(25, '2025-03-25', 'NV005', 'DG005', 25, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sach`
--

CREATE TABLE `sach` (
  `MaSach` varchar(10) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT NULL,
  `MaDauSach` varchar(10) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 1,
  `ngaynhap` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sach`
--

INSERT INTO `sach` (`MaSach`, `TrangThai`, `MaDauSach`, `Status`, `ngaynhap`) VALUES
('S001', 0, 'DS001', 1, '2025-01-01'),
('S002', 1, 'DS002', 1, '2025-01-01'),
('S003', 2, 'DS003', 1, '2025-01-01'),
('S004', 0, 'DS004', 1, '2025-01-01'),
('S005', 1, 'DS005', 1, '2025-01-01'),
('S006', 2, 'DS006', 1, '2025-01-01'),
('S007', 0, 'DS007', 1, '2025-01-01'),
('S008', 1, 'DS008', 1, '2025-01-01'),
('S009', 2, 'DS009', 1, '2025-01-01'),
('S010', 0, 'DS010', 1, '2025-01-01'),
('S011', 1, 'DS011', 1, '2025-01-01'),
('S012', 2, 'DS012', 1, '2025-01-01'),
('S013', 0, 'DS013', 1, '2025-01-01'),
('S014', 1, 'DS014', 1, '2025-01-01'),
('S015', 2, 'DS015', 1, '2025-01-01'),
('S016', 0, 'DS016', 1, '2025-01-01'),
('S017', 1, 'DS017', 1, '2025-01-01'),
('S018', 2, 'DS018', 1, '2025-01-01'),
('S019', 0, 'DS019', 1, '2025-01-01'),
('S020', 1, 'DS020', 1, '2025-01-01'),
('S021', 2, 'DS021', 1, '2025-01-01'),
('S022', 0, 'DS022', 1, '2025-01-01'),
('S023', 1, 'DS023', 1, '2025-01-01'),
('S024', 2, 'DS024', 1, '2025-01-01'),
('S025', 0, 'DS025', 1, '2025-01-01'),
('S026', 1, 'DS001', 1, '2025-01-01'),
('S027', 2, 'DS002', 1, '2025-01-01'),
('S028', 0, 'DS003', 1, '2025-01-01'),
('S029', 1, 'DS004', 1, '2025-01-01'),
('S030', 2, 'DS005', 1, '2025-01-01'),
('S031', 0, 'DS006', 1, '2025-01-01'),
('S032', 1, 'DS007', 1, '2025-01-01'),
('S033', 2, 'DS008', 1, '2025-01-01'),
('S034', 0, 'DS009', 1, '2025-01-01'),
('S035', 1, 'DS010', 1, '2025-01-01'),
('S036', 2, 'DS011', 1, '2025-01-01'),
('S037', 0, 'DS012', 1, '2025-01-01'),
('S038', 1, 'DS013', 1, '2025-01-01'),
('S039', 2, 'DS014', 1, '2025-01-01'),
('S040', 0, 'DS015', 1, '2025-01-01'),
('S041', 1, 'DS016', 1, '2025-01-01'),
('S042', 2, 'DS017', 1, '2025-01-01'),
('S043', 0, 'DS018', 1, '2025-01-01'),
('S044', 1, 'DS019', 1, '2025-01-01'),
('S045', 2, 'DS020', 1, '2025-01-01'),
('S046', 0, 'DS021', 1, '2025-01-01'),
('S047', 1, 'DS022', 1, '2025-01-01'),
('S048', 2, 'DS023', 1, '2025-01-01'),
('S049', 0, 'DS024', 1, '2025-01-01'),
('S050', 1, 'DS025', 1, '2025-01-01'),
('S051', 2, 'DS001', 1, '2025-01-01'),
('S052', 0, 'DS002', 1, '2025-01-01'),
('S053', 1, 'DS003', 1, '2025-01-01'),
('S054', 2, 'DS004', 1, '2025-01-01'),
('S055', 0, 'DS005', 1, '2025-01-01'),
('S056', 1, 'DS006', 1, '2025-01-01'),
('S057', 2, 'DS007', 1, '2025-01-01'),
('S058', 0, 'DS008', 1, '2025-01-01'),
('S059', 1, 'DS009', 1, '2025-01-01'),
('S060', 2, 'DS010', 1, '2025-01-01'),
('S061', 0, 'DS011', 1, '2025-01-01'),
('S062', 1, 'DS012', 1, '2025-01-01'),
('S063', 2, 'DS013', 1, '2025-01-01'),
('S064', 0, 'DS014', 1, '2025-01-01'),
('S065', 1, 'DS015', 1, '2025-01-01'),
('S066', 2, 'DS016', 1, '2025-01-01'),
('S067', 0, 'DS017', 1, '2025-01-01'),
('S068', 1, 'DS018', 1, '2025-01-01'),
('S069', 2, 'DS019', 1, '2025-01-01'),
('S070', 0, 'DS020', 1, '2025-01-01'),
('S071', 1, 'DS021', 1, '2025-01-01'),
('S072', 2, 'DS022', 1, '2025-01-01'),
('S073', 0, 'DS023', 1, '2025-01-01'),
('S074', 1, 'DS024', 1, '2025-01-01'),
('S075', 2, 'DS025', 1, '2025-01-01'),
('S076', 0, 'DS001', 1, '2025-01-01'),
('S077', 1, 'DS002', 1, '2025-01-01'),
('S078', 2, 'DS003', 1, '2025-01-01'),
('S079', 0, 'DS004', 1, '2025-01-01'),
('S080', 1, 'DS005', 1, '2025-01-01'),
('S081', 2, 'DS006', 1, '2025-01-01'),
('S082', 0, 'DS007', 1, '2025-01-01'),
('S083', 1, 'DS008', 1, '2025-01-01'),
('S084', 2, 'DS009', 1, '2025-01-01'),
('S085', 0, 'DS010', 1, '2025-01-01'),
('S086', 1, 'DS011', 1, '2025-01-01'),
('S087', 2, 'DS012', 1, '2025-01-01'),
('S088', 0, 'DS013', 1, '2025-01-01'),
('S089', 1, 'DS014', 1, '2025-01-01'),
('S090', 2, 'DS015', 1, '2025-01-01'),
('S091', 0, 'DS016', 1, '2025-01-01'),
('S092', 1, 'DS017', 1, '2025-01-01'),
('S093', 2, 'DS018', 1, '2025-01-01'),
('S094', 0, 'DS019', 1, '2025-01-01'),
('S095', 1, 'DS020', 1, '2025-01-01'),
('S096', 2, 'DS021', 1, '2025-01-01'),
('S097', 0, 'DS022', 1, '2025-01-01'),
('S098', 1, 'DS023', 1, '2025-01-01'),
('S099', 2, 'DS024', 1, '2025-01-01'),
('S100', 0, 'DS025', 1, '2025-01-01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tacgia`
--

CREATE TABLE `tacgia` (
  `MaTacGia` varchar(10) NOT NULL,
  `TenTacGia` varchar(100) NOT NULL,
  `NamSinh` int(11) DEFAULT NULL,
  `QuocTich` varchar(50) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tacgia`
--

INSERT INTO `tacgia` (`MaTacGia`, `TenTacGia`, `NamSinh`, `QuocTich`, `Status`) VALUES
('TG001', 'Paulo Coelho', 1947, 'Brazil', 1),
('TG002', 'Dale Carnegie', 1888, 'Mỹ', 1),
('TG003', 'J.K. Rowling', 1965, 'Anh', 1),
('TG004', 'Colleen McCullough', 1937, 'Úc', 1),
('TG005', 'Mario Puzo', 1920, 'Mỹ', 1),
('TG006', 'Rosie Nguyễn', 1986, 'Việt Nam', 1),
('TG007', 'Adam Khoo', 1974, 'Singapore', 1),
('TG008', 'David J. Lieberman', 1969, 'Mỹ', 1),
('TG009', 'Tony Buổi Sáng', NULL, 'Việt Nam', 1),
('TG010', 'Arthur Conan Doyle', 1859, 'Anh', 1),
('TG011', 'Andrea Hirata', 1967, 'Indonesia', 1),
('TG012', 'Stephen Hawking', 1942, 'Anh', 1),
('TG013', 'Victor Hugo', 1802, 'Pháp', 1),
('TG014', 'Hồ Chí Minh', 1890, 'Việt Nam', 1),
('TG015', 'Nguyễn Nhật Ánh', 1955, 'Việt Nam', 1),
('TG016', 'Antoine de Saint-Exupéry', 1900, 'Pháp', 1),
('TG017', 'Tô Hoài', 1920, 'Việt Nam', 1),
('TG018', 'Viktor E. Frankl', 1905, 'Áo', 1),
('TG019', 'J.D. Salinger', 1919, 'Mỹ', 1),
('TG020', 'Ernest Hemingway', 1899, 'Mỹ', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tacgia_sach`
--

CREATE TABLE `tacgia_sach` (
  `MaTacGia` varchar(10) NOT NULL,
  `MaSach` varchar(10) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tacgia_sach`
--

INSERT INTO `tacgia_sach` (`MaTacGia`, `MaSach`, `Status`) VALUES
('TG001', 'S001', 1),
('TG001', 'S002', 1),
('TG001', 'S021', 1),
('TG001', 'S041', 1),
('TG001', 'S061', 1),
('TG001', 'S081', 1),
('TG002', 'S003', 1),
('TG002', 'S022', 1),
('TG002', 'S042', 1),
('TG002', 'S062', 1),
('TG002', 'S082', 1),
('TG003', 'S004', 1),
('TG003', 'S023', 1),
('TG003', 'S043', 1),
('TG003', 'S063', 1),
('TG003', 'S083', 1),
('TG004', 'S005', 1),
('TG004', 'S024', 1),
('TG004', 'S044', 1),
('TG004', 'S064', 1),
('TG004', 'S084', 1),
('TG005', 'S006', 1),
('TG005', 'S025', 1),
('TG005', 'S045', 1),
('TG005', 'S065', 1),
('TG005', 'S085', 1),
('TG006', 'S007', 1),
('TG006', 'S026', 1),
('TG006', 'S046', 1),
('TG006', 'S066', 1),
('TG006', 'S086', 1),
('TG007', 'S008', 1),
('TG007', 'S027', 1),
('TG007', 'S047', 1),
('TG007', 'S067', 1),
('TG007', 'S087', 1),
('TG008', 'S009', 1),
('TG008', 'S028', 1),
('TG008', 'S048', 1),
('TG008', 'S068', 1),
('TG008', 'S088', 1),
('TG009', 'S010', 1),
('TG009', 'S029', 1),
('TG009', 'S049', 1),
('TG009', 'S069', 1),
('TG009', 'S089', 1),
('TG010', 'S011', 1),
('TG010', 'S030', 1),
('TG010', 'S050', 1),
('TG010', 'S070', 1),
('TG010', 'S090', 1),
('TG011', 'S012', 1),
('TG011', 'S031', 1),
('TG011', 'S051', 1),
('TG011', 'S071', 1),
('TG011', 'S091', 1),
('TG012', 'S013', 1),
('TG012', 'S032', 1),
('TG012', 'S052', 1),
('TG012', 'S072', 1),
('TG012', 'S092', 1),
('TG013', 'S014', 1),
('TG013', 'S033', 1),
('TG013', 'S053', 1),
('TG013', 'S073', 1),
('TG013', 'S093', 1),
('TG014', 'S015', 1),
('TG014', 'S034', 1),
('TG014', 'S054', 1),
('TG014', 'S074', 1),
('TG014', 'S094', 1),
('TG015', 'S016', 1),
('TG015', 'S035', 1),
('TG015', 'S055', 1),
('TG015', 'S075', 1),
('TG015', 'S095', 1),
('TG016', 'S017', 1),
('TG016', 'S036', 1),
('TG016', 'S056', 1),
('TG016', 'S076', 1),
('TG016', 'S096', 1),
('TG017', 'S018', 1),
('TG017', 'S037', 1),
('TG017', 'S057', 1),
('TG017', 'S077', 1),
('TG017', 'S097', 1),
('TG018', 'S019', 1),
('TG018', 'S038', 1),
('TG018', 'S058', 1),
('TG018', 'S078', 1),
('TG018', 'S098', 1),
('TG019', 'S020', 1),
('TG019', 'S039', 1),
('TG019', 'S059', 1),
('TG019', 'S079', 1),
('TG019', 'S099', 1),
('TG020', 'S040', 1),
('TG020', 'S060', 1),
('TG020', 'S080', 1),
('TG020', 'S100', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `TenDangNhap` varchar(20) NOT NULL,
  `MatKhau` varchar(100) NOT NULL,
  `TrangThai` int(11) NOT NULL CHECK (`TrangThai` in (0,1)),
  `Email` varchar(100) DEFAULT NULL,
  `MaNhomQuyen` int(11) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`TenDangNhap`, `MatKhau`, `TrangThai`, `Email`, `MaNhomQuyen`, `Status`) VALUES
('DG001', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg001@email.com', 3, 1),
('DG002', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg002@email.com', 3, 1),
('DG003', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg003@email.com', 3, 1),
('DG004', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg004@email.com', 3, 1),
('DG005', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg005@email.com', 3, 1),
('DG006', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg006@email.com', 3, 1),
('DG007', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg007@email.com', 3, 1),
('DG008', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg008@email.com', 3, 1),
('DG009', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg009@email.com', 3, 1),
('DG010', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg010@email.com', 3, 1),
('DG011', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg011@email.com', 3, 1),
('DG012', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg012@email.com', 3, 1),
('DG013', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg013@email.com', 3, 1),
('DG014', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg014@email.com', 3, 1),
('DG015', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg015@email.com', 3, 1),
('DG016', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg016@email.com', 3, 1),
('DG017', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg017@email.com', 3, 1),
('DG018', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg018@email.com', 3, 1),
('DG019', 'e10adc3949ba59abbe56e057f20f883e', 1, 'dg019@email.com', 3, 1),
('DG020', 'e10adc3949ba59abbe56e057f20f883e', 0, 'dg020@email.com', 3, 1),
('NV001', 'e10adc3949ba59abbe56e057f20f883e', 1, 'nv001@bookstore.com', 1, 1),
('NV002', 'e10adc3949ba59abbe56e057f20f883e', 1, 'nv002@bookstore.com', 1, 1),
('NV003', 'e10adc3949ba59abbe56e057f20f883e', 1, 'nv003@bookstore.com', 2, 1),
('NV004', 'e10adc3949ba59abbe56e057f20f883e', 1, 'nv004@bookstore.com', 2, 1),
('NV005', 'e10adc3949ba59abbe56e057f20f883e', 1, 'nv005@bookstore.com', 2, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `theloai`
--

CREATE TABLE `theloai` (
  `MaTheLoai` int(11) NOT NULL,
  `TenTheLoai` varchar(50) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `theloai`
--

INSERT INTO `theloai` (`MaTheLoai`, `TenTheLoai`, `Status`) VALUES
(1, 'Tiểu thuyết', 1),
(2, 'Tâm lý - Kỹ năng sống', 1),
(3, 'Kinh doanh', 1),
(4, 'Khoa học', 1),
(5, 'Văn học cổ điển', 1),
(6, 'Trinh thám', 1),
(7, 'Self-help', 1),
(8, 'Văn học thiếu nhi', 1),
(9, 'Lịch sử', 1),
(10, 'Triết học', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `theloaidausach`
--

CREATE TABLE `theloaidausach` (
  `MaDauSach` varchar(10) NOT NULL,
  `MaTheLoai` int(11) NOT NULL,
  `Status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `theloaidausach`
--

INSERT INTO `theloaidausach` (`MaDauSach`, `MaTheLoai`, `Status`) VALUES
('DS001', 1, 1),
('DS001', 7, 1),
('DS002', 2, 1),
('DS002', 7, 1),
('DS003', 8, 1),
('DS004', 1, 1),
('DS005', 1, 1),
('DS006', 2, 1),
('DS007', 2, 1),
('DS007', 7, 1),
('DS008', 2, 1),
('DS009', 2, 1),
('DS009', 3, 1),
('DS010', 5, 1),
('DS010', 6, 1),
('DS011', 1, 1),
('DS012', 4, 1),
('DS013', 5, 1),
('DS014', 2, 1),
('DS015', 1, 1),
('DS016', 4, 1),
('DS016', 9, 1),
('DS017', 5, 1),
('DS018', 5, 1),
('DS019', 8, 1),
('DS020', 8, 1),
('DS021', 8, 1),
('DS022', 2, 1),
('DS022', 7, 1),
('DS023', 2, 1),
('DS024', 1, 1),
('DS025', 5, 1),
('DS026', 1, 1),
('DS027', 3, 1),
('DS028', 2, 1),
('DS029', 2, 1),
('DS029', 10, 1),
('DS030', 2, 1),
('DS031', 3, 1),
('DS031', 7, 1),
('DS032', 2, 1),
('DS032', 4, 1),
('DS033', 2, 1),
('DS033', 4, 1),
('DS034', 2, 1),
('DS035', 3, 1),
('DS035', 7, 1),
('DS036', 3, 1),
('DS037', 2, 1),
('DS038', 1, 1),
('DS039', 2, 1),
('DS040', 3, 1),
('DS041', 3, 1),
('DS042', 3, 1),
('DS043', 2, 1),
('DS043', 3, 1),
('DS044', 2, 1),
('DS045', 2, 1),
('DS046', 3, 1),
('DS047', 3, 1),
('DS048', 3, 1),
('DS049', 3, 1),
('DS049', 9, 1),
('DS050', 2, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chucnang`
--
ALTER TABLE `chucnang`
  ADD PRIMARY KEY (`MaChucNang`);

--
-- Chỉ mục cho bảng `ctphieumuon`
--
ALTER TABLE `ctphieumuon`
  ADD PRIMARY KEY (`MaPhieuMuon`,`MaSach`),
  ADD KEY `MaSach` (`MaSach`);

--
-- Chỉ mục cho bảng `ctphieutra`
--
ALTER TABLE `ctphieutra`
  ADD PRIMARY KEY (`MaCTPhieuTra`),
  ADD KEY `FK_MaPhieuTra` (`MaPhieuTra`),
  ADD KEY `FK_MaSach` (`MaSach`);

--
-- Chỉ mục cho bảng `cttheloai`
--
ALTER TABLE `cttheloai`
  ADD PRIMARY KEY (`MaTheLoai`);

--
-- Chỉ mục cho bảng `dausach`
--
ALTER TABLE `dausach`
  ADD PRIMARY KEY (`MaDauSach`);

--
-- Chỉ mục cho bảng `docgia`
--
ALTER TABLE `docgia`
  ADD PRIMARY KEY (`MaDocGia`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MaNCC`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`MaNhanVien`);

--
-- Chỉ mục cho bảng `nhomquyen`
--
ALTER TABLE `nhomquyen`
  ADD PRIMARY KEY (`MaNhomQuyen`);

--
-- Chỉ mục cho bảng `nhomquyen_chucnang`
--
ALTER TABLE `nhomquyen_chucnang`
  ADD PRIMARY KEY (`MaNhomQuyen`,`MaChucNang`),
  ADD KEY `MaChucNang` (`MaChucNang`);

--
-- Chỉ mục cho bảng `phieumuon`
--
ALTER TABLE `phieumuon`
  ADD PRIMARY KEY (`MaPhieuMuon`),
  ADD KEY `MaDocGia` (`MaDocGia`),
  ADD KEY `MaNhanVien` (`MaNhanVien`);

--
-- Chỉ mục cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`MaPhieuNhap`),
  ADD KEY `MaNCC` (`MaNCC`),
  ADD KEY `MaNhanVien` (`MaNhanVien`);

--
-- Chỉ mục cho bảng `phieuphat`
--
ALTER TABLE `phieuphat`
  ADD PRIMARY KEY (`MaPhieuPhat`),
  ADD KEY `MaDocGia` (`MaDocGia`),
  ADD KEY `MaCTPhieuTra` (`MaCTPhieuTra`);

--
-- Chỉ mục cho bảng `phieutra`
--
ALTER TABLE `phieutra`
  ADD PRIMARY KEY (`MaPhieuTra`),
  ADD KEY `MaDocGia` (`MaDocGia`),
  ADD KEY `MaPhieuMuon` (`MaPhieuMuon`),
  ADD KEY `MaNhanVien` (`MaNhanVien`);

--
-- Chỉ mục cho bảng `sach`
--
ALTER TABLE `sach`
  ADD PRIMARY KEY (`MaSach`),
  ADD KEY `MaDauSach` (`MaDauSach`);

--
-- Chỉ mục cho bảng `tacgia`
--
ALTER TABLE `tacgia`
  ADD PRIMARY KEY (`MaTacGia`);

--
-- Chỉ mục cho bảng `tacgia_sach`
--
ALTER TABLE `tacgia_sach`
  ADD PRIMARY KEY (`MaTacGia`,`MaSach`),
  ADD KEY `MaSach` (`MaSach`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`TenDangNhap`),
  ADD KEY `MaNhomQuyen` (`MaNhomQuyen`);

--
-- Chỉ mục cho bảng `theloai`
--
ALTER TABLE `theloai`
  ADD PRIMARY KEY (`MaTheLoai`);

--
-- Chỉ mục cho bảng `theloaidausach`
--
ALTER TABLE `theloaidausach`
  ADD PRIMARY KEY (`MaDauSach`,`MaTheLoai`),
  ADD KEY `MaTheLoai` (`MaTheLoai`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `cttheloai`
--
ALTER TABLE `cttheloai`
  MODIFY `MaTheLoai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `phieumuon`
--
ALTER TABLE `phieumuon`
  MODIFY `MaPhieuMuon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT cho bảng `phieutra`
--
ALTER TABLE `phieutra`
  MODIFY `MaPhieuTra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT cho bảng `theloai`
--
ALTER TABLE `theloai`
  MODIFY `MaTheLoai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `ctphieumuon`
--
ALTER TABLE `ctphieumuon`
  ADD CONSTRAINT `ctphieumuon_ibfk_1` FOREIGN KEY (`MaPhieuMuon`) REFERENCES `phieumuon` (`MaPhieuMuon`),
  ADD CONSTRAINT `ctphieumuon_ibfk_2` FOREIGN KEY (`MaSach`) REFERENCES `sach` (`MaSach`);

--
-- Các ràng buộc cho bảng `ctphieutra`
--
ALTER TABLE `ctphieutra`
  ADD CONSTRAINT `FK_MaPhieuTra` FOREIGN KEY (`MaPhieuTra`) REFERENCES `phieutra` (`MaPhieuTra`),
  ADD CONSTRAINT `FK_MaSach` FOREIGN KEY (`MaSach`) REFERENCES `sach` (`MaSach`);

--
-- Các ràng buộc cho bảng `nhomquyen_chucnang`
--
ALTER TABLE `nhomquyen_chucnang`
  ADD CONSTRAINT `nhomquyen_chucnang_ibfk_1` FOREIGN KEY (`MaNhomQuyen`) REFERENCES `nhomquyen` (`MaNhomQuyen`),
  ADD CONSTRAINT `nhomquyen_chucnang_ibfk_2` FOREIGN KEY (`MaChucNang`) REFERENCES `chucnang` (`MaChucNang`);

--
-- Các ràng buộc cho bảng `phieumuon`
--
ALTER TABLE `phieumuon`
  ADD CONSTRAINT `phieumuon_ibfk_1` FOREIGN KEY (`MaDocGia`) REFERENCES `docgia` (`MaDocGia`),
  ADD CONSTRAINT `phieumuon_ibfk_2` FOREIGN KEY (`MaNhanVien`) REFERENCES `nhanvien` (`MaNhanVien`);

--
-- Các ràng buộc cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `phieunhap_ibfk_1` FOREIGN KEY (`MaNCC`) REFERENCES `nhacungcap` (`MaNCC`),
  ADD CONSTRAINT `phieunhap_ibfk_2` FOREIGN KEY (`MaNhanVien`) REFERENCES `nhanvien` (`MaNhanVien`);

--
-- Các ràng buộc cho bảng `phieuphat`
--
ALTER TABLE `phieuphat`
  ADD CONSTRAINT `phieuphat_ibfk_1` FOREIGN KEY (`MaDocGia`) REFERENCES `docgia` (`MaDocGia`),
  ADD CONSTRAINT `phieuphat_ibfk_2` FOREIGN KEY (`MaCTPhieuTra`) REFERENCES `ctphieutra` (`MaCTPhieuTra`);

--
-- Các ràng buộc cho bảng `phieutra`
--
ALTER TABLE `phieutra`
  ADD CONSTRAINT `phieutra_ibfk_1` FOREIGN KEY (`MaDocGia`) REFERENCES `docgia` (`MaDocGia`),
  ADD CONSTRAINT `phieutra_ibfk_2` FOREIGN KEY (`MaPhieuMuon`) REFERENCES `phieumuon` (`MaPhieuMuon`),
  ADD CONSTRAINT `phieutra_ibfk_3` FOREIGN KEY (`MaNhanVien`) REFERENCES `nhanvien` (`MaNhanVien`);

--
-- Các ràng buộc cho bảng `sach`
--
ALTER TABLE `sach`
  ADD CONSTRAINT `sach_ibfk_1` FOREIGN KEY (`MaDauSach`) REFERENCES `dausach` (`MaDauSach`);

--
-- Các ràng buộc cho bảng `tacgia_sach`
--
ALTER TABLE `tacgia_sach`
  ADD CONSTRAINT `tacgia_sach_ibfk_1` FOREIGN KEY (`MaTacGia`) REFERENCES `tacgia` (`MaTacGia`),
  ADD CONSTRAINT `tacgia_sach_ibfk_2` FOREIGN KEY (`MaSach`) REFERENCES `sach` (`MaSach`);

--
-- Các ràng buộc cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `taikhoan_ibfk_1` FOREIGN KEY (`MaNhomQuyen`) REFERENCES `nhomquyen` (`MaNhomQuyen`);

--
-- Các ràng buộc cho bảng `theloaidausach`
--
ALTER TABLE `theloaidausach`
  ADD CONSTRAINT `theloaidausach_ibfk_1` FOREIGN KEY (`MaDauSach`) REFERENCES `dausach` (`MaDauSach`),
  ADD CONSTRAINT `theloaidausach_ibfk_2` FOREIGN KEY (`MaTheLoai`) REFERENCES `theloai` (`MaTheLoai`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
