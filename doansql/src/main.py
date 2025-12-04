from tool.fds_tool import closure_stack, check_fd, candidate_keys_stack, bcnf_decompose_stack, equivalent_fd


def main():
    print("=== Functional Dependency Tool ===")

    R = set(input("Nhập các thuộc tính, cách nhau bởi dấu phẩy: ").replace(" ", "").split(","))

    FDs = []
    n = int(input("Nhập số lượng phụ thuộc hàm: "))
    for i in range(n):
        left = set(input(f"FD {i + 1} - vế trái (các thuộc tính cách nhau dấu phẩy): ").replace(" ", "").split(","))
        right = set(input(f"FD {i + 1} - vế phải (các thuộc tính cách nhau dấu phẩy): ").replace(" ", "").split(","))
        FDs.append((left, right))

    while True:
        print("\n--- Menu ---")
        print("1: Tính bao đóng X⁺")
        print("2: Kiểm tra FD X → Y")
        print("3: Tìm khóa ứng viên")
        print("4: Kiểm tra & phân rã BCNF")
        print("5: Kiểm tra FD equivalent")
        print("0: Thoát")
        choice = input("Chọn thao tác: ")

        if choice == "1":
            X = set(input("Nhập tập X (các thuộc tính cách nhau dấu phẩy): ").replace(" ", "").split(","))
            print(f"{X}+ = {closure_stack(X, FDs)}")

        elif choice == "2":
            X = set(input("Nhập X (vế trái): ").replace(" ", "").split(","))
            Y = set(input("Nhập Y (vế phải): ").replace(" ", "").split(","))
            result = check_fd(X, Y, FDs)
            print(f"{X} → {Y} {'đúng' if result else 'sai'}")

        elif choice == "3":
            keys = candidate_keys_stack(R, FDs)
            print("Khóa ứng viên:")
            for k in keys:
                print(k)

        elif choice == "4":
            bcnf = bcnf_decompose_stack(R, FDs)
            print("BCNF decomposition:")
            for rel in bcnf:
                print(rel)

        elif choice == "5":
            X1 = set(input("Nhập X1 (FD1 vế trái): ").replace(" ", "").split(","))
            Y1 = set(input("Nhập Y1 (FD1 vế phải): ").replace(" ", "").split(","))
            X2 = set(input("Nhập X2 (FD2 vế trái): ").replace(" ", "").split(","))
            Y2 = set(input("Nhập Y2 (FD2 vế phải): ").replace(" ", "").split(","))
            result = equivalent_fd((X1, Y1), (X2, Y2), FDs)
            print(f"FD1 và FD2 {'equivalent' if result else 'không equivalent'}")

        elif choice == "0":
            print("Thoát chương trình.")
            break
        else:
            print("Lựa chọn không hợp lệ!")


if __name__ == "__main__":
    main()