package com.vuducminh.stylash.service;

import com.vuducminh.stylash.model.Voucher;
import com.vuducminh.stylash.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService{
    private final VoucherRepository voucherRepository;

    @Override
    public Voucher getVoucherByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    @Override
    public List<Voucher> getVoucherByCodeContaining(String name) {
        return voucherRepository.findByCodeContaining(name);
    }

    @Override
    public BigDecimal getVoucherValueByCode(String voucherCode) {
        Voucher voucher = voucherRepository.findByCode(voucherCode);
        if(voucher!=null){
            return voucher.getDiscount();
        }
        else {
            return null;
        }
    }

    public void useVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    @Override
    public Optional<Voucher> getVoucherById(Long id) {
        return voucherRepository.findById(id);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        voucherRepository.delete(voucher);
    }
}
