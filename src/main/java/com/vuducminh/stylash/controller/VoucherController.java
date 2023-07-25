package com.vuducminh.stylash.controller;

import com.vuducminh.stylash.model.Voucher;
import com.vuducminh.stylash.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Voucher>> getAllVoucher(@RequestParam(value = "name", required = false) String name) {
        List<Voucher> vouchers;
        if (name != null) {
            vouchers = voucherService.getVoucherByCodeContaining(name);
        } else {
            vouchers = voucherService.getAllVouchers();
        }
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Optional<Voucher>> getVoucherById(@PathVariable Long id) {
        return ResponseEntity.ok(voucherService.getVoucherById(id));
    }

    @PostMapping("/check_voucher")
    public ResponseEntity<BigDecimal> getVoucherByCode(@RequestParam("voucherCode") String voucherCode) {
        Voucher voucher = voucherService.getVoucherByCode(voucherCode);
        if (voucher != null) {
            return ResponseEntity.ok(voucher.getDiscount());
        } else {
            return ResponseEntity.ok(BigDecimal.valueOf(0));
        }
    }

//    @PostMapping("/{code}/use")
//    public ResponseEntity<Void> useVoucher(@PathVariable("code") String code) {
//        Voucher voucher = voucherService.getVoucherByCode(code);
//        if (voucher != null && voucher.getStatus() == VoucherStatus.UNUSED) {
//            voucherService.useVoucher(voucher);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
}