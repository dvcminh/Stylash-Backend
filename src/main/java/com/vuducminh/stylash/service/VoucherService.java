package com.vuducminh.stylash.service;

import com.vuducminh.stylash.model.Voucher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface VoucherService {
    Voucher getVoucherByCode(String code);
    List<Voucher> getVoucherByCodeContaining(String name);
    BigDecimal getVoucherValueByCode(String voucherCode);
    void useVoucher(Voucher voucher);
    Optional<Voucher> getVoucherById(Long id);
    List<Voucher> getAllVouchers();
    Voucher updateVoucher(Voucher voucher);
    void deleteVoucher(Voucher voucher);
}
