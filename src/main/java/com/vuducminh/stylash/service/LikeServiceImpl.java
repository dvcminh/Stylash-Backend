package com.vuducminh.stylash.service;

import com.vuducminh.stylash.model.Like;
import com.vuducminh.stylash.model.Product;
import com.vuducminh.stylash.repository.LikeRepository;
import com.vuducminh.stylash.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void unlikeProduct(User user, Product product) {
        likeRepository.deleteByUserAndProduct(user, product);
    }

    @Override
    public boolean isProductLikedByUser(User user, Product product) {
        return likeRepository.existsByUserAndProduct(user, product);
    }

    @Override
    public Like likeProduct(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public Optional<Like> findById(Integer id) {
        return likeRepository.findById(id);
    }

    @Override
    public List<Like> findByProductId(Integer id) {
        return likeRepository.findByProductId(id);
    }

    @Override
    public List<Like> getAll() {
        return likeRepository.findAll();
    }

    @Override
    public List<Like> findByNameContaining(String name) {
        return likeRepository.findByUserEmailContaining(name);
    }

    @Override
    public void detele(Like like) {
        likeRepository.delete(like);
    }

    @Override
    public List<Like> findByUserId(Integer id) {
        return likeRepository.findByUserId(id);
    }
}

