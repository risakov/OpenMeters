//
//  Requests.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation
import RxNetworkApiClient


extension ExtendedApiRequest {

   
    // MARK: - File

//    static func uploadImages(_ images: [Data]) -> ExtendedApiRequest {
//        var data = [UploadFile]()
//        for image in images {
//            data.append(UploadFile("image\(image).jpg", image, "image"))
//        }
//        return extendedRequest(path: "/api/uploadImages",
//                               method: .post,
//                               headers: [Header.contentJson],
//                               body: ["photos": data])
//
//    }
    static func uploadSingleImage(_ image: Data) -> ExtendedApiRequest {
        return extendedRequest(path: "/api/uploadSingleImage",
                               method: .post,
                               headers: [Header.contentJson],
                               body: UploadFile("image\(image).jpg", image, "image"))
    }
    


}

