# Graph initializing full chess board and all chess pieces
# This graph doesn't change
# STATIC
import rdflib
from rdflib import URIRef, Namespace, Literal
from rdflib.namespace import RDF

g = rdflib.Graph()
gT1 = rdflib.Graph()
gT2 = rdflib.Graph()


ns = Namespace("http://example.org/chess/")

###############################################################################################
# Initialise pieces


pieces = []

# eg piece
#
# :P13 a :Pawn;
#   a :ChessPiece;
#   :hasColour :white.
current_colour = URIRef(ns["white"])

for i in range(32):

    current_piece = URIRef(ns["P" + str(i+1)])
    pieces.append(current_piece)
    # define the type of piece
    match i:
        case 0 | 16:
            g.add((current_piece, RDF.type, ns.King))
            gT1.add((current_piece, ns.type_t1, ns.King))
            gT2.add((current_piece, ns.type_t2, ns.King))

        case 1 | 17:
            g.add((current_piece, RDF.type, ns.Queen))
            gT1.add((current_piece, ns.type_t1, ns.Queen))
            gT2.add((current_piece, ns.type_t2, ns.Queen))

        case 2 | 3 | 18 | 19:
            g.add((current_piece, RDF.type, ns.Bishop))
            gT1.add((current_piece, ns.type_t1, ns.Bishop))
            gT2.add((current_piece, ns.type_t2, ns.Bishop))

        case 4 | 5 | 20 |21:
            g.add((current_piece, RDF.type, ns.Knight))
            gT1.add((current_piece, ns.type_t1, ns.Knight))
            gT2.add((current_piece, ns.type_t2, ns.Knight))

        case 6 | 7 | 22 | 23:
            g.add((current_piece, RDF.type, ns.Rook))
            gT1.add((current_piece, ns.type_t1, ns.Rook))
            gT2.add((current_piece, ns.type_t2, ns.Rook))
        case _:
            g.add((current_piece, RDF.type, ns.Pawn))
            gT1.add((current_piece, ns.type_t1, ns.Pawn))
            gT2.add((current_piece, ns.type_t2, ns.Pawn))

    g.add((current_piece, RDF.type, ns.ChessPiece))
    gT1.add((current_piece, RDF.type, ns.ChessPiece))
    gT2.add((current_piece, RDF.type, ns.ChessPiece))
    if i == 16:
        current_colour = URIRef(ns["black"])
    g.add((current_piece, ns.hasColour, current_colour))
    gT1.add((current_piece, ns.hasColour, current_colour))
    gT2.add((current_piece, ns.hasColour, current_colour))





print(g.serialize(format="turtle"))
g.serialize(destination="../piecesInfoW1.ttl")
g.serialize(destination="initPiecesInfoW1.ttl")
gT1.serialize(destination="../piecesInfoW2.ttl")
gT1.serialize(destination="initPiecesInfoW2.ttl")
gT2.serialize(destination="../piecesInfoW3.ttl")
gT2.serialize(destination="initPiecesInfoW3.ttl")
